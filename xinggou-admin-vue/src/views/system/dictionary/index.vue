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
        @click="handleDetele"
      >删除</el-button>
    </div>
    <el-table
      ref="tableData"
      :data="dictData"
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
        label="ID"
      />
      <el-table-column
        prop="code"
        label="字典码"
      />
      <el-table-column
        prop="createDate"
        label="创建日"
      />
      <el-table-column
        prop="name"
        label="字典名称"
      />
      <el-table-column
        prop="remarks"
        label="备注"
      />
      <el-table-column
        prop="seqNo"
        label="排序"
      />
      <el-table-column
        prop="type"
        label="字典类型"
      />
      <el-table-column
        prop="value"
        label="字典值"
      />
      <el-table-column
        label="操作"
        min-width="220"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            @click="handleEdit(scope.$index, scope.row)"
          >编辑</el-button>
        </template>
      </el-table-column>
    </el-table>
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="listQuery.currentPage"
      :limit.sync="listQuery.pageSize"
      @pagination="getList"
    />
    <el-dialog
      :title="dialogStatus == 'update' ? '编辑字典':'新增字典'"
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
          hidden
          label="id"
          prop="id"
        >
          <el-input v-model="temp.id" />
        </el-form-item>
        <el-form-item
          label="字典码"
          prop="code"
        >
          <el-input v-model="temp.code" />
        </el-form-item>
        <el-form-item
          label="字典名称"
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
        <el-form-item
          label="排序"
          prop="seqNo"
        >
          <el-input v-model="temp.seqNo" />
        </el-form-item>
        <el-form-item
          label="字典类型"
          prop="type"
        >
          <el-input v-model="temp.type" />
        </el-form-item>
        <el-form-item
          label="字典值"
          prop="value"
        >
          <el-input v-model="temp.value" />
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
  dictDelete,
  // dictInfo,
  dictList,
  dictSave
} from '@/api/system/dictionary.js'

import Pagination from '@/components/Pagination' // secondary package based on el-pagination

export default {
  // import引入的组件需要注入到对象中才能使用
  components: { Pagination },
  data() {
    // 这里存放数据
    return {
      dictData: [],
      listQuery: {
        currentPage: 1,
        pageSize: 20,
        name: '',
        type: '',
        offset: ''
      },
      total: 0,
      rules: {},
      dialogStatus: '',
      dialogFormVisible: false,
      temp: {
        id: '',
        name: '',
        remarks: '',
        seqNo: '',
        type: '',
        value: ''
      },
      selection: []
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
  mounted() {

  },
  beforeCreate() { }, // 生命周期 - 创建之前
  beforeMount() { }, // 生命周期 - 挂载之前
  beforeUpdate() { }, // 生命周期 - 更新之前
  updated() { }, // 生命周期 - 更新之后
  beforeDestroy() { }, // 生命周期 - 销毁之前
  destroyed() { }, // 生命周期 - 销毁完成
  activated() { },
  // 方法集合
  methods: {
    handleCreate() {
      this.resetTemp()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    handleEdit(index, row) {
      this.temp = Object.assign({}, row)
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    resetTemp() {
      this.temp = {
        id: '',
        name: '',
        remarks: '',
        seqNo: '',
        type: '',
        value: ''
      }
    },
    createData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          dictSave(this.temp).then((res) => {
            this.dictData.unshift(this.temp)
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
          dictSave(this.temp).then((res) => {
            const index = this.dictData.findIndex(
              (v) => v.id === this.temp.id
            )
            this.dictData.splice(index, 1, this.temp)
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
    handleDetele() {
      if (this.selection.length) {
        const ids = this.selection.map((item) => {
          return item.id
        })
        this.$confirm(`确定是否删除字典`, '提示', {
          confirmButtonText: '删除',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          dictDelete({ ids: ids }).then(() => {
            this.dictData = this.dictData.filter((item) => {
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
    handleSelectionChange(val) {
      this.selection = val
    },
    getList() {
      dictList(this.listQuery).then((res) => {
        this.dictData = res.data.list
        this.total = res.data.totalCount
      })
    }
  } // 如果页面有keep-alive缓存功能，这个函数会触发
}
</script>
<style lang='sass' scoped>
//@import url(); 引入公共css类
</style>
