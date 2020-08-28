<!--  -->
<template>
  <div class="app-container">
    <div class="filter-container">
      <el-button
        class="filter-item"
        size="mini"
        type="primary"
        icon="el-icon-edit"
        @click="handleCreate"
      >增加</el-button>
    </div>
    <el-table
      :data="tableData"
      style="width: 100%;margin-bottom: 20px;"
      row-key="menuId"
      border
      :expand-row-keys="expandKeys"
      :tree-props="{children: 'childNodes', hasChildren: 'hasChildren'}"
    >
      <el-table-column
        prop="name"
        label="菜单名称"
      />
      <el-table-column
        prop="menuId"
        label="菜单ID"
      />
      <el-table-column
        prop="parentId"
        label="父级ID"
      />
      <el-table-column
        prop="level"
        label="菜单等级"
      />
      <el-table-column
        prop="icon"
        label="菜单图标"
      />
      <el-table-column
        prop="perms"
        label="授权"
      />
      <el-table-column
        prop="type"
        label="菜单类型"
      >
        <template slot-scope="scope">{{ scope.row.type == 10 ? "目录" : (scope.row.type == 20 ? "菜单" : "按钮") }}</template>
      </el-table-column>
      <el-table-column
        prop="url"
        label="菜单url"
      />
      <el-table-column
        label="操作"
        min-width="220"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            @click="handleCreate(scope.$index, scope.row)"
          >新增</el-button>
          <el-button
            size="mini"
            @click="handleEdit(scope.$index, scope.row)"
          >编辑</el-button>
          <el-button
            size="mini"
            type="danger"
            @click="handleDelete(scope.$index, scope.row)"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog
      :title="dialogStatus == 'update' ? '编辑菜单':'新增菜单'"
      :visible.sync="dialogFormVisible"
      :close-on-click-modal="false"
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
          label="菜单图标"
          prop="icon"
        >
          <el-input v-model="temp.icon" />
        </el-form-item>
        <el-form-item
          label="名称"
          prop="name"
        >
          <el-input v-model="temp.name" />
        </el-form-item>
        <el-form-item
          label="父级ID"
          prop="parentId"
        >
          <el-input
            v-model="temp.parentId"
            :disabled="true"
          />
        </el-form-item>
        <el-form-item
          label="授权"
          prop="perms"
        >
          <el-input v-model="temp.perms" />
        </el-form-item>
        <el-form-item
          label="排序"
          prop="seqNo"
        >
          <el-input v-model="temp.seqNo" />
        </el-form-item>
        <el-form-item
          v-if="dialogStatus=='create'"
          label="菜单类型"
          prop="type"
        >
          <el-select
            v-model="temp.type"
            class="filter-item"
            placeholder=""
          >
            <el-option
              v-for="item in calendarTypeOptions"
              :key="item.key"
              :label="item.display_name"
              :value="item.key"
            />
          </el-select>
        </el-form-item>
        <el-form-item
          label="菜单url"
          prop="url"
        >
          <el-input v-model="temp.url" />
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
  </div>
</template>

<script>
// 这里可以导入其他文件（比如：组件，工具js，第三方插件js，json文件，图片文件等等）
// 例如：import 《组件名称》 from '《组件路径》';

import {
  menuCreate,
  menuDelete,
  // menuInfo,
  menuTree,
  menuUpdate
} from '@/api/system/menu.js'

const calendarTypeOptions = [
  { key: '10', display_name: '目录' },
  { key: '20', display_name: '菜单' },
  { key: '30', display_name: '按钮' }
]

export default {
  // import引入的组件需要注入到对象中才能使用
  components: {},
  data() {
    // 这里存放数据
    return {
      tableData: [],
      expandKeys: [],
      temp: {
        icon: '',
        name: '',
        parentId: '',
        perms: '',
        seqNo: '',
        type: '',
        url: ''
      },
      calendarTypeOptions,
      dialogStatus: '',
      dialogFormVisible: false,
      rules: {}
    }
  },
  // 监听属性 类似于data概念
  computed: {},
  // 监控data中的数据变化
  watch: {},
  // 生命周期 - 创建完成（可以访问当前this实例）
  created() {
    this.menuTree()
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
    menuTree() {
      menuTree().then((res) => {
        this.tableData = res.data.childNodes
        this.expandKeys.push(JSON.stringify(this.tableData[0].menuId))
      })
    },
    handleCreate(index, row) {
      this.resetTemp()
      console.log(row)
      this.temp.parentId = row ? row.menuId : 0
      this.dialogStatus = 'create'
      this.dialogFormVisible = true

      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    handleEdit(index, row) {
      this.temp = Object.assign({}, row) // copy obj

      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    handleDelete(index, row) {
      this.$confirm(`确定是否删除菜单 [${row.name}] `, '提示', {
        confirmButtonText: '删除',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        menuDelete({ id: row.menuId }).then(() => {
          this.menuTree()
        })
      })
    },
    resetTemp() {
      this.temp = {
        icon: '',
        name: '',
        parentId: 0,
        perms: '',
        seqNo: '',
        type: '',
        url: ''
      }
    },
    createData() {
      menuCreate(this.temp).then((res) => {
        this.menuTree()
        this.dialogFormVisible = false
      })
    },
    updateData() {
      menuUpdate(this.temp).then((res) => {
        this.menuTree()
        this.dialogFormVisible = false
      })
    }

  } // 如果页面有keep-alive缓存功能，这个函数会触发
}
</script>
<style lang='sass' scoped>
//@import url(); 引入公共css类
</style>
