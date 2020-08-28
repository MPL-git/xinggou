<!--  -->
<template>
  <div class="app-container">
    <div class="filter-container">
      角色名称:
      <el-input
        v-model="listQuery.roleName"
        style="width:100px;margin-right:10px"
        size="mini"
      />角色类型:
      <el-select
        v-model="roleType"
        size="mini"
        placeholder="请选择"
      >
        <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        />
      </el-select>
    </div>
    <div class="filter-container">
      <el-button
        class="filter-item"
        size="mini"
        type="primary"
        icon="el-icon-edit"
        @click="handleCreate"
      >增加</el-button>
      <el-button
        class="filter-item"
        size="mini"
        type="primary"
        icon="el-icon-edit"
        @click="handleEdit"
      >修改</el-button>
      <el-button
        class="filter-item"
        size="mini"
        type="primary"
        icon="el-icon-edit"
        @click="handleDetele"
      >删除</el-button>
      <el-button
        class="filter-item"
        size="mini"
        type="primary"
        icon="el-icon-edit"
        @click="handleMenu"
      >菜单权限</el-button>
    </div>
    <el-table
      ref="multipleTable"
      :data="roleData"
      tooltip-effect="dark"
      style="width: 100%"
      border
      @selection-change="handleSelectionChange"
    >
      <el-table-column
        type="selection"
        width="55"
      />
      <el-table-column
        prop="id"
        label="角色ID"
      />
      <el-table-column
        prop="assignedStaffs"
        label="已做关联的员工"
      />
      <el-table-column
        prop="createDate"
        label="创建日期"
      />
      <el-table-column
        prop="name"
        label="角色名称"
      />
      <el-table-column
        prop="remarks"
        label="备注"
      />
      <el-table-column
        prop="updateDate"
        label="更新日期"
      />
    </el-table>
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="listQuery.page"
      :limit.sync="listQuery.limit"
      @pagination="getList"
    />

    <el-dialog
      :title="dialogStatus == 'update' ? '编辑角色':'新增角色'"
      :close-on-click-modal="false"
      :visible.sync="dialogFormVisible"
    >
      <el-form
        ref="dataForm"
        :rules="rules"
        :model="temp"
        size="mini"
        label-position="left"
        label-width="90px"
        style="width: 400px; margin-left:50px;"
      >
        <el-form-item
          hidden
          label="角色ID"
          prop="id"
        >
          <el-input v-model="temp.id" />
        </el-form-item>
        <el-form-item
          label="角色名称"
          prop="name"
        >
          <el-input v-model="temp.name" />
        </el-form-item>
        <el-form-item
          label="备注"
          prop="remarks"
        >
          <el-input v-model="temp.remarks" />
        </el-form-item>
      </el-form>
      <div
        slot="footer"
        class="dialog-footer"
      >
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button
          type="primary"
          @click="dialogStatus==='create'?createData():updateData()"
        >确认</el-button>
      </div>
    </el-dialog>
    <el-dialog
      title="菜单权限"
      :visible.sync="dialogMenuVisible"
      :close-on-click-modal="false"
    >
      <el-tree
        ref="tree"
        :props="props"
        :data="menuData"
        node-key="menuId"
        check-strictly
        show-checkbox
        :default-expanded-keys="expandedId"
        :default-checked-keys="ownMenuIdList"
        :defa="ownMenuIdList"
      />
      <div
        slot="footer"
        class="dialog-footer"
      >
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button
          type="primary"
          @click="menuSave"
        >保存</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
// 这里可以导入其他文件（比如：组件，工具js，第三方插件js，json文件，图片文件等等）
// 例如：import 《组件名称》 from '《组件路径》';
import {
  roleDelete,
  // roleInfo,
  roleList,
  rolePermissionTree,
  rolePermissionUpdate,
  roleSave
} from '@/api/system/role.js'

import Pagination from '@/components/Pagination' // secondary package based on el-pagination

export default {
  // import引入的组件需要注入到对象中才能使用
  components: { Pagination },
  data() {
    // 这里存放数据
    return {
      roleType: '',
      options: [
        {
          value: '选项1',
          label: '黄金糕'
        },
        {
          value: '选项2',
          label: '双皮奶'
        },
        {
          value: '选项3',
          label: '蚵仔煎'
        },
        {
          value: '选项4',
          label: '龙须面'
        },
        {
          value: '选项5',
          label: '北京烤鸭'
        }
      ],
      roleData: [],
      multipleSelection: [],
      listQuery: {
        page: 1,
        limit: 20,
        roleName: ''
      },
      total: 0,
      dialogStatus: '',
      dialogFormVisible: false,
      dialogMenuVisible: false,
      rules: {
        name: [{ required: true, message: '不能为空', trigger: 'blur' }]
      },
      temp: {
        name: '',
        remarks: '',
        roleId: ''
      },

      props: {
        label: 'name',
        children: 'childNodes'
      },
      count: 1,
      menuData: [],
      expandedId: [1008],
      ownMenuIdList: []
    }
  },
  // 监听属性 类似于data概念
  computed: {},
  // 监控data中的数据变化
  watch: {},
  // 生命周期 - 创建完成（可以访问当前this实例）
  created() {
    this.getList()
  },
  // 生命周期 - 挂载完成（可以访问DOM元素）
  mounted() { },
  beforeCreate() { }, // 生命周期 - 创建之前
  beforeMount() { }, // 生命周期 - 挂载之前
  beforeUpdate() { }, // 生命周期 - 更新之前
  updated() { }, // 生命周期 - 更新之后
  beforeDestroy() { }, // 生命周期 - 销毁之前
  destroyed() { }, // 生命周期 - 销毁完成
  activated() { },
  // 方法集合
  methods: {
    getList() {
      roleList({
        currentPage: this.listQuery.page,
        pageSize: this.listQuery.limit,
        name: this.listQuery.roleName
      }).then((res) => {
        this.roleData = res.data.list
        this.total = res.data.totalCount
      })
    },
    handleSelectionChange(val) {
      console.log(val)
      this.multipleSelection = val
    },
    resetTemp() {
      this.temp = {
        name: '',
        remarks: '',
        roleId: null
      }
    },
    handleCreate() {
      this.resetTemp()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    createData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          roleSave(this.temp).then((res) => {
            this.roleData.unshift(this.temp)
            this.dialogFormVisible = false
            this.$notify({
              title: '成功',
              message: '添加成功',
              type: 'success',
              duration: 2000
            })
          })
        }
      })
    },
    updateData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.temp.roleId = this.temp.id
          roleSave(this.temp).then((res) => {
            // const index = this.roleData.findIndex((v) => v.id === this.temp.id);
            // this.roleData.splice(index, 1, this.temp);

            this.dialogFormVisible = false
            this.$notify({
              title: '成功',
              message: '修改成功',
              type: 'success',
              duration: 2000
            })
          })
        }
      })
    },
    handleEdit() {
      // eslint-disable-next-line eqeqeq
      if (this.multipleSelection.length != 1) {
        this.$message({
          message: '请勾选一行数据',
          type: 'error',
          duration: 2 * 1000
        })
      } else {
        this.temp = this.multipleSelection[0]
        this.dialogStatus = 'update'
        this.dialogFormVisible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].clearValidate()
        })
      }
    },
    handleDetele() {
      if (this.multipleSelection.length) {
        const ids = this.multipleSelection.map((item) => {
          return item.id
        })
        this.$confirm(`确定是否删除角色，系统将会移除相关用户已绑定角色的记录`, '提示', {
          confirmButtonText: '删除',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          roleDelete({ ids: ids }).then(() => {
            this.roleData = this.roleData.filter((item) => {
              // eslint-disable-next-line eqeqeq
              return ids.indexOf(item.id) == -1
            })
          })
        })
      } else {
        this.$message({
          message: '请勾选至少一行数据',
          type: 'error',
          duration: 2 * 1000
        })
      }
    },
    handleMenu() {
      // eslint-disable-next-line eqeqeq
      if (this.multipleSelection.length != 1) {
        this.$message({
          message: '请勾选一行数据',
          type: 'error',
          duration: 2 * 1000
        })
      } else {
        this.dialogMenuVisible = true
        rolePermissionTree({ id: this.multipleSelection[0].id }).then((res) => {
          this.menuData = res.data.tree.childNodes
          this.ownMenuIdList = res.data.ownMenuIdList
          this.expandedId = [res.data.tree.childNodes[0].menuId]
        })
      }
    },
    menuSave() {
      console.log(this.$refs.tree.getCheckedKeys())
      rolePermissionUpdate({
        menuIds: this.$refs.tree.getCheckedKeys(),
        roleId: this.multipleSelection[0].id
      }).then((res) => {
        this.$notify({
          title: '成功',
          message: '授权成功',
          type: 'success',
          duration: 2000
        })
      })
    }
  } // 如果页面有keep-alive缓存功能，这个函数会触发
}
</script>
<style lang='sass' scoped>
//@import url(); 引入公共css类
</style>
