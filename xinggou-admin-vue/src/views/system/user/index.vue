<template>
  <div class="app-container">
    <el-row
      type="flex"
      :gutter="20"
      class="row-bg"
    >
      <el-col :span="4">
        <el-tree
          :data="deptTreeData"
          :props="defaultProps"
          @node-click="handleNodeClick"
        />
      </el-col>
      <el-col :span="20">
        <div class="filter-container">
          <el-button
            class="filter-item"
            size="mini"
            type="primary"
            icon="el-icon-edit"
            @click="handleCreate"
          >增加人员</el-button>
          <el-button
            class="filter-item"
            size="mini"
            type="primary"
            icon="el-icon-edit"
            @click="handlePassword"
          >密码重置</el-button>
          <el-button
            class="filter-item"
            size="mini"
            type="primary"
            icon="el-icon-edit"
            @click="handleRole"
          >人员角色权限</el-button>
        </div>

        <el-table
          v-loading="listLoading"
          :data="tableData"
          highlight-current-row
          size="mini"
          border
          style="width: 100%"
          @current-change="handleCurrentChange"
        >
          <el-table-column
            prop="id"
            label="职工ID"
          />
          <el-table-column
            prop="userName"
            label="用户名"
          />
          <el-table-column
            prop="birthday"
            label="生日"
          />
          <el-table-column
            prop="deptName"
            label="部门名称"
          />
          <el-table-column
            prop="entryTimeStr"
            label="入职时间"
          />
          <el-table-column
            prop="exitTimeStr"
            label="离职时间"
          />
          <el-table-column
            prop="gender"
            label="性别"
          >
            <template slot-scope="scope">
              <div v-if="scope.row.gender == 10">男</div>
              <div v-if="scope.row.gender == 20">女</div>
              <div v-if="scope.row.gender == 30">未知</div>
            </template>
          </el-table-column>
          <el-table-column
            prop="mobile"
            label="手机号"
          />
          <el-table-column
            prop="nickName"
            label="昵称"
          />
          <el-table-column
            prop="position"
            label="职位"
          />
          <el-table-column
            prop="positionStatusDesc"
            label="职位状态"
          />
          <el-table-column
            prop="realName"
            label="真实姓名"
          />
          <el-table-column
            prop="statusDesc"
            label="帐号状态"
          >
            <template slot-scope="scope">
              <div v-if="scope.row.status == 20">禁用</div>
              <div v-if="scope.row.status == 10">正常</div>
            </template>
          </el-table-column>
          <el-table-column
            prop="email"
            label="邮箱"
          />
          <el-table-column
            label="操作"
            min-width="210"
          >
            <template slot-scope="scope">
              <el-button
                v-if="scope.row.status == 20"
                size="mini"
                @click="handleActive(scope.$index, scope.row)"
              >激活</el-button>
              <el-button
                v-if="scope.row.status == 10"
                size="mini"
                @click="handleDisabled(scope.$index, scope.row)"
              >禁用</el-button>
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
        <pagination
          v-show="total>0"
          :total="total"
          :page.sync="listQuery.page"
          :limit.sync="listQuery.limit"
          @pagination="fetchData"
        />
      </el-col>
    </el-row>

    <el-dialog
      :title="dialogStatus == 'update' ? '编辑员工':'新增员工'"
      :visible.sync="dialogFormVisible"
      :close-on-click-modal="false"
    >
      <el-form
        ref="dataForm"
        :rules="rules"
        :model="temp"
        size="mini"
        label-position="left"
        label-width="70px"
        style="width: 400px; margin-left:50px;"
      >
        <el-form-item
          label="ID"
          prop="id"
        >
          <el-input v-model="temp.id" />
        </el-form-item>
        <el-form-item
          label="头像"
          prop="avatar"
        >
          <el-input
            v-model="temp.avatar"
            style="width:200px"
          />

          <el-button
            type="primary"
            icon="el-icon-upload"
            @click="imagecropperShow=true"
          >
            上传头像
          </el-button>

          <image-cropper
            v-show="imagecropperShow"
            :key="imagecropperKey"
            :value="false"
            :width="300"
            :height="300"
            url="/rc/oss/upload"
            @close="close"
            @crop-upload-success="cropSuccess"
          />

        </el-form-item>
        <el-form-item
          label="生日"
          prop="birthday"
        >
          <el-date-picker
            v-model="temp.birthday"
            type="date"
            placeholder="请选择日期"
          />
        </el-form-item>
        <el-form-item
          label="邮箱"
          prop="email"
        >
          <el-input v-model="temp.email" />
        </el-form-item>
        <el-form-item
          label="性别"
          prop="gender"
        >
          <el-select
            v-model="temp.gender"
            class="filter-item"
            placeholder="请选择性别"
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
          label="手机号"
          prop="mobile"
        >
          <el-input v-model="temp.mobile" />
        </el-form-item>
        <el-form-item
          label="昵称"
          prop="nickName"
        >
          <el-input v-model="temp.nickName" />
        </el-form-item>

        <el-form-item
          label="职位"
          prop="position"
        >
          <el-input v-model="temp.position" />
        </el-form-item>
        <el-form-item
          label="真实姓名"
          prop="realName"
        >
          <el-input v-model="temp.realName" />
        </el-form-item>
        <el-form-item
          label="用户名"
          prop="userName"
        >
          <el-input v-model="temp.userName" />
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
      title="人员角色授权"
      :visible.sync="dialogRoleVisible"
      :close-on-click-modal="false"
    >
      <div class="role-content">
        <div
          class="search"
          style="margin-bottom:10px"
        >
          角色名称:
          <el-input
            v-model="roleName"
            style="width:100px;margin-right:10px"
            size="mini"
          />
          <el-button
            class="filter-item"
            size="mini"
            type="primary"
            icon="el-icon-edit"
            @click="roleSearch"
          >搜索</el-button>
          <el-button
            class="filter-item"
            size="mini"
            type="primary"
            icon="el-icon-edit"
            @click="roleAdd"
          >授权</el-button>
          <el-button
            class="filter-item"
            size="mini"
            type="primary"
            icon="el-icon-edit"
            @click="roleRemove"
          >注销授权</el-button>
        </div>

        <div class="btns" />
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
            prop="bound"
            label="授权"
          >
            <template slot-scope="scope">
              <div v-if="scope.row.bound">已授权</div>
            </template>
          </el-table-column>
          <el-table-column
            prop="id"
            label="角色标识"
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
            prop
            label="角色类型"
          />
          <el-table-column
            prop
            label="创建人"
          />
          <el-table-column
            prop="createDate"
            label="创建时间"
          />
        </el-table>
        <pagination
          v-show="roleRotal>0"
          :total="roleRotal"
          :page.sync="roleQuery.page"
          :limit.sync="roleQuery.limit"
          @pagination="handleRole"
        />
      </div>
      <div
        slot="footer"
        class="dialog-footer"
      >
        <el-button @click="dialogRoleVisible = false">取消</el-button>
        <el-button
          type="primary"
          @click="updateRole()"
        >确认</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  staffList,
  staffCreate,
  staffUpdate,
  staffActive,
  staffDelete,
  staffDisabled,
  staffPasswordReset,
  staffRoleList,
  staffRoleAdd,
  staffRoleRemove
} from '@/api/system/user.js'

import { deptTree } from '@/api/system/department.js'

import { parseTime } from '@/utils'

import ImageCropper from '@/components/ImageCropper'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination
const calendarTypeOptions = [
  { key: '10', display_name: '男' },
  { key: '20', display_name: '女' },
  { key: '30', display_name: '未知' }
]
export default {
  name: 'UserTable',
  components: { Pagination, ImageCropper },
  data() {
    return {
      tableData: [],
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 20,
        deptId: '',
        deptName: '',
        importance: undefined,
        title: undefined,
        type: undefined,
        sort: '+id'
      },
      temp: {
        avatar: '',
        birthday: new Date(),
        deptId: '',
        email: '',
        gender: '30',
        nickName: '',
        password: '',
        position: '',
        realName: '',
        userName: '',
        mobile: '',
        roleIdList: []
      },
      dialogFormVisible: false,
      dialogRoleVisible: false,
      calendarTypeOptions,
      dialogStatus: '',
      textMap: {
        update: 'Edit',
        create: 'Create'
      },
      rules: {
        avatar: [{ required: true, message: '不能为空', trigger: 'change' }],
        email: [
          { required: true, message: '不能为空', trigger: 'blur' },
          { type: 'email', message: '格式不正确', trigger: 'change' }
        ],
        gender: [{ required: true, message: '不能为空', trigger: 'change' }],
        nickName: [{ required: true, message: '不能为空', trigger: 'blur' }],
        position: [{ required: true, message: '不能为空', trigger: 'blur' }],
        realName: [{ required: true, message: '不能为空', trigger: 'blur' }],
        userName: [{ required: true, message: '不能为空', trigger: 'blur' }],
        mobile: [{ required: true, message: '不能为空', trigger: 'blur' }]
      },
      defaultProps: {
        children: 'childNodes',
        label: 'name'
      },
      roleName: '',
      deptTreeData: [],
      currentRow: null,
      roleData: [],
      multipleSelection: [],
      imagecropperShow: false,
      imagecropperKey: 0,
      roleQuery: {
        page: 1,
        limit: 20
      },
      roleRotal: 0
    }
  },
  created() {
    this.deptTree()
    this.fetchData()
  },
  methods: {
    resetTemp() {
      this.temp = {
        avatar: '',
        birthday: new Date(),
        deptId: '',
        deptName: '',
        email: '',
        gender: '30',
        nickName: '',
        password: '',
        position: '',
        realName: '',
        userName: '',
        mobile: '',
        roleIdList: []
      }
    },
    handleCurrentChange(val) {
      this.currentRow = val
      console.log(val)
    },
    handlePassword() {
      if (this.currentRow) {
        this.$confirm(`确定是否重置用户 [${this.currentRow.userName}] 密码`, '提示', {
          confirmButtonText: '重置',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          staffPasswordReset({
            id: this.currentRow.id
          }).then(() => {
            this.$notify({
              title: '成功',
              message: `用户[${this.currentRow.userName}]重置密码成功`,
              type: 'success',
              duration: 2000
            })
          })
        })
      } else {
        this.$message({
          message: '选择用户',
          type: 'error',
          duration: 2 * 1000
        })
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
    handleRole() {
      if (this.currentRow) {
        this.dialogRoleVisible = true
        staffRoleList({
          boundType: 0,
          currentPage: this.roleQuery.page,
          pageSize: this.roleQuery.limit,
          roleName: this.roleName,
          staffId: this.currentRow.id
        }).then((res) => {
          console.log(res)
          this.roleData = res.data.list
        })
      } else {
        this.$message({
          message: '选择用户',
          type: 'error',
          duration: 2 * 1000
        })
      }
    },
    handleEdit(index, row) {
      this.temp = Object.assign({}, row) // copy obj
      this.$set(this.temp, 'birthday', new Date(this.temp.birthday))

      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    updateRole() {
      this.dialogRoleVisible = false
    },
    handleDelete(index, row) {
      console.log(index, row)
      staffDelete({ ids: [row.id] }).then(() => {
        this.$notify({
          title: '成功',
          message: '删除成功',
          type: 'success',
          duration: 2000
        })
        this.tableData.splice(index, 1)
      })
    },
    handleActive(index, row) {
      staffActive({ id: row.id }).then(() => {
        row.status = 10
        row.statusDesc = '正常'
        this.$notify({
          title: '成功',
          message: '激活成功',
          type: 'success',
          duration: 2000
        })
        this.tableData.splice(index, 1, row)
      })
    },
    handleDisabled(index, row) {
      staffDisabled({ id: row.id }).then(() => {
        row.status = 20
        row.statusDesc = '禁用'
        this.$notify({
          title: '成功',
          message: '禁用成功',
          type: 'success',
          duration: 2000
        })
        this.tableData.splice(index, 1, row)
      })
    },
    fetchData() {
      this.listLoading = true
      staffList({
        deptId: this.listQuery.deptId,
        currentPage: this.listQuery.page,
        pageSize: this.listQuery.limit
      }).then((response) => {
        this.tableData = response.data.list
        this.total = response.data.totalCount
        setTimeout(() => {
          this.listLoading = false
        }, 0.5 * 1000)
      })
    },
    deptTree() {
      deptTree({}).then((response) => {
        console.log(response)
        this.deptTreeData = response.data.childNodes
        this.listQuery.deptId = this.temp.deptId = this.deptTreeData[0].deptId
        this.listQuery.deptName = this.temp.deptName = this.deptTreeData[0].name
      })
    },
    createData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          console.log(this.temp)
          const tempData = this.temp
          tempData.birthday = parseTime(
            tempData.birthday,
            '{y}-{m}-{d}'
          )
          tempData.deptId = this.listQuery.deptId
          tempData.deptName = this.listQuery.deptName
          tempData.positionStatusDesc = '在职'
          tempData.status = '10'
          staffCreate(tempData).then(() => {
            this.tableData.unshift(tempData)
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
          const tempData = Object.assign({}, this.temp)
          tempData.birthday = parseTime(
            tempData.birthday,
            '{y}-{m}-{d}'
          )
          console.log(tempData)

          staffUpdate(tempData).then(() => {
            const index = this.tableData.findIndex(
              (v) => v.id === this.temp.id
            )
            this.tableData.splice(index, 1, tempData)
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
    handleNodeClick(data) {
      console.log(data)
      this.listQuery.deptId = data.deptId
      this.fetchData()
    },
    handleSelectionChange(val) {
      console.log(val)
      this.multipleSelection = val
    },
    roleSearch() {
      this.handleRole()
    },
    roleAdd() {
      console.log(this.multipleSelection)
      const ids = this.multipleSelection.map((item) => {
        return item.id
      })
      staffRoleAdd({ roleIdList: ids, staffId: this.currentRow.id }).then(() => {
        this.handleRole()
        this.$notify({
          title: '成功',
          message: '授权成功',
          type: 'success',
          duration: 2000
        })
      })
    },
    roleRemove() {
      console.log(this.multipleSelection)
      const ids = this.multipleSelection.map((item) => {
        return item.id
      })
      staffRoleRemove({ roleIdList: ids, staffId: this.currentRow.id }).then(() => {
        this.handleRole()
        this.$notify({
          title: '成功',
          message: '注销成功',
          type: 'success',
          duration: 2000
        })
      })
    },
    cropSuccess(resData) {
      this.imagecropperShow = false
      this.imagecropperKey = this.imagecropperKey + 1
      this.temp.avatar = resData
    },
    close() {
      this.imagecropperShow = false
    }
  }
}
</script>

<style scoped>
.line {
  text-align: center;
}
.avatar {
  width: 200px;
  height: 200px;
  border-radius: 50%;
}
</style>

