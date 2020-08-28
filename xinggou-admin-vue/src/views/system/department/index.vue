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
    </div>
    <el-tree
      :data="deptTreeData"
      :props="defaultProps"
      @node-click="handleNodeClick"
    />
    <el-dialog
      :title="dialogStatus == 'update' ? '编辑部门':'新增部门'"
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
          hidden="false"
          label="父级部门ID"
          prop="parentId"
        >
          <el-input v-model="temp.parentId" />
        </el-form-item>
        <el-form-item
          hidden="false"
          label="部门ID"
          prop="id"
        >
          <el-input v-model="temp.id" />
        </el-form-item>
        <el-form-item
          label="部门名称"
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
  </div>
</template>

<script>
// 这里可以导入其他文件（比如：组件，工具js，第三方插件js，json文件，图片文件等等）
// 例如：import 《组件名称》 from '《组件路径》';
import {
  deptTree,
  deptCreate,
  deptDelete,
  deptInfo,
  deptUpdate
} from '@/api/system/department.js'

export default {
  // import引入的组件需要注入到对象中才能使用
  components: {},
  data() {
    // 这里存放数据
    return {
      selectDep: {},
      deptTreeData: [],
      defaultProps: {
        children: 'childNodes',
        label: 'name'
      },
      dialogStatus: 0,
      dialogFormVisible: false,
      rules: {
        name: [{ required: true, message: '不能为空', trigger: 'blur' }]
      },
      temp: {
        parentId: '',
        name: '',
        remarks: '',
        id: ''
      }
    }
  },
  // 监听属性 类似于data概念
  computed: {},
  // 监控data中的数据变化
  watch: {},
  // 生命周期 - 创建完成（可以访问当前this实例）
  created() {
    this.deptTree()
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
    deptTree() {
      deptTree({}).then((response) => {
        console.log(response)
        this.deptTreeData = response.data.childNodes
        this.selectDep = this.deptTreeData[0]
      })
    },
    handleNodeClick(data) {
      console.log(data)
      this.selectDep = data
    },
    handleCreate() {
      if (this.selectDep.deptId) {
        this.resetTemp()
        this.dialogStatus = 'create'
        this.dialogFormVisible = true
        this.temp.parentId = this.selectDep.deptId
        this.$nextTick(() => {
          this.$refs['dataForm'].clearValidate()
        })
      } else {
        this.$message({
          message: '请选择上级部门',
          type: 'error',
          duration: 2 * 1000
        })
      }
    },
    handleEdit() {
      if (this.selectDep.deptId) {
        this.dialogStatus = 'update'
        this.dialogFormVisible = true
        deptInfo({ id: this.selectDep.deptId }).then((res) => {
          this.temp = res.data
        })
        this.$nextTick(() => {
          this.$refs['dataForm'].clearValidate()
        })
      } else {
        this.$message({
          message: '请选择编辑部门',
          type: 'error',
          duration: 2 * 1000
        })
      }
    },
    handleDetele() {
      if (this.selectDep.deptId) {
        this.$confirm(`确定是否删除 [${this.selectDep.name}] 部门`, '提示', {
          confirmButtonText: '删除',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          deptDelete({ ids: [this.selectDep.deptId] }).then(() => {
            this.deptTree()
            this.$notify({
              title: '成功',
              message: '删除成功',
              type: 'success',
              duration: 2000
            })
          })
        })
      } else {
        this.$message({
          message: '请选择删除部门',
          type: 'error',
          duration: 2 * 1000
        })
      }
    },
    resetTemp() {
      this.temp = {
        parentId: '',
        name: '',
        remarks: '',
        id: ''
      }
    },
    createData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          deptCreate(this.temp).then(() => {
            this.deptTree()
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
          const tempData = this.temp
          tempData.deptId = this.temp.id
          deptUpdate(tempData).then(() => {
            this.deptTree()
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
    }
  } // 如果页面有keep-alive缓存功能，这个函数会触发
}
</script>
<style lang='sass' scoped>
//@import url(); 引入公共css类
</style>
