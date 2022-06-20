import {
  getList,
  saveUser,
  remove,
  setRole,
  changeStatus,
  resetPassword,
  editUser
} from '@/api/system/user'
import {
  getToken
} from '@/utils/auth'

const roleArray = ['admin', 'user']
export default {
  name: 'mgr',
  data() {
    return {
      roleArray: roleArray,
      roleDialog: {
        visible: false,
        roles: [{
          roleId: 1,
          roleName: 'admin'
        }, {
          roleId: 2,
          roleName: 'user'
        }],
        checkedRoleKeys: [],
        defaultProps: {
          id: 'roleId',
          label: 'roleName'
        }
      },
      editDialog: {
        visible: false,
        title: '修改用户'
      },
      formVisible: false,
      formTitle: '添加用户',
      roleSet: [],
      form: {
        username: '',
        password: '',
        nickname: '',
        phone: '',
        email: '',
        avatar: ''
      },
      rules: {
        username: [{
          required: true,
          message: '请输入登录账号',
          trigger: 'blur'
        },
        {
          min: 5,
          max: 20,
          message: '长度在 5 到 20 个字符',
          trigger: 'blur'
        }
        ],
        password: [{
          required: false,
          message: '请输入登录密码',
          trigger: 'blur'
        },
        {
          min: 5,
          max: 20,
          message: '长度在 5 到 20 个字符',
          trigger: 'blur'
        }
        ],
        nickname: [{
          required: true,
          message: '请输入用户昵称',
          trigger: 'blur'
        },
        {
          min: 2,
          max: 20,
          message: '长度在 2 到 20 个字符',
          trigger: 'blur'
        }
        ],
        phone: [{
          required: false,
          message: '请输入phone',
          trigger: 'blur'
        }],
        email: [{
          required: false,
          message: '请输入email',
          trigger: 'blur'
        }]
      },
      listQuery: {
        page: 1,
        limit: 5,
        username: '',
        role: '',
        nickname: '',
        phone: '',
        email: ''
      },
      options: [{
        value: '选项1',
        label: '系统管理员'
      }, {
        value: '选项2',
        label: '普通用户'
      }],
      value: '',
      role: '',
      avatarloading: false,
      backImgloading: false,
      total: 0,
      list: null,
      listLoading: true,
      selRow: {}
    }
  },
  filters: {
    statusFilter(status) {
      const statusMap = {
        published: 'success',
        draft: 'gray',
        deleted: 'danger'
      }
      return statusMap[status]
    }
  },
  created() {
    this.init()
  },
  methods: {
    // 初始化
    init() {
      this.fetchData()
    },
    // 获取用户信息
    fetchData() {
      this.listLoading = true
      getList(this.listQuery).then(response => {
        this.list = response.data.dataCurrentPage
        this.listLoading = false
        this.total = response.data.totalCount
      })
    },
    getRole() {
      if (this.role === '选项1') {
        this.listQuery.role = '系统管理员'
      }else if(this.role === '选项2') {
        this.listQuery.role = '普通用户'
      }
    },
    // 搜索
    search() {
      this.listQuery.page = 1
      this.getRole()
      this.fetchData()
    },
    // 重置
    reset() {
      this.listQuery.username = ''
      this.listQuery.role = ''
      this.listQuery.nickname = ''
      this.listQuery.page = 1
      this.listQuery.phone = ''
      this.listQuery.email = ''
      this.fetchData()
    },
    // 过滤
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    },
    handleClose() {

    },
    fetchNext() {
      this.listQuery.page = this.listQuery.page + 1
      this.fetchData()
    },
    fetchPrev() {
      this.listQuery.page = this.listQuery.page - 1
      this.fetchData()
    },
    fetchPage(page) {
      this.listQuery.page = page
      this.fetchData()
    },
    changeSize(limit) {
      this.listQuery.limit = limit
      this.fetchData()
    },
    handleCurrentChange(currentRow, oldCurrentRow) {
      this.selRow = currentRow
    },
    resetForm() {
      this.form = {
        username: '',
        password: '',
        nickname: '',
        phone: '',
        email: '',
        avatar: '',
        backImg: '',
        signature: '',
        enable: true
      }
    },
    add() {
      this.resetForm()
      this.formTitle = '添加用户'
      this.formVisible = true
    },
    changeUserStatus(userId, enable) {
      var params = {
        'userId': userId,
        'enable': enable
      }
      changeStatus(params).then(() => {
        this.$notify({
          title: '成功',
          message: '修改成功',
          type: 'success'
        })
        this.fetchData()
      }).catch(() => {
        this.fetchData()
      })
    },
    saveUser() {
      var self = this
      this.$refs['form'].validate((valid) => {
        if (valid) {
          var form = self.form
          var roleSet = []
          for (const role of this.roleSet) {
            if (role == 'admin') {
              roleSet.push({
                'roleId': 1
              })
            } else if (role == 'user') {
              roleSet.push({
                'roleId': 2
              })
            }
          }
          form.roleSet = roleSet
          console.log(form)
          saveUser(form).then(response => {
            this.$message({
              message: '提交成功',
              type: 'success'
            })
            this.fetchData()
            this.formVisible = false
          })
        } else {
          return false
        }
      })
    },
    checkSel() {
      if (this.selRow && this.selRow.id) {
        return true
      }
      this.$message({
        message: '请选中操作项',
        type: 'warning'
      })
      return false
    },
    editItem(record) {
      this.selRow = Object.assign({}, record)
      this.edit()
    },
    edit() {
      if (this.checkSel()) {
        const form = Object.assign({}, this.selRow)
        form.password = ''
        const newForm = {
          id: form.id,
          username: form.username,
          password: '',
          nickname: form.nickname,
          phone: form.phone,
          email: form.email,
          avatar: form.avatar,
          roleSet: form.roleSet
        }
        this.form = newForm
        this.formTitle = '修改用户'
        this.editDialog.visible = true
      }
    },
    editUser() {
      var self = this
      this.$refs['form'].validate((valid) => {
        if (valid) {
          var form = self.form
          var roleSet = []
          for (const role of this.roleSet) {
            if (role == 'admin') {
              roleSet.push({
                'roleId': 1
              })
            } else if (role == 'user') {
              roleSet.push({
                'roleId': 2
              })
            }
          }
          form.roleSet = roleSet
          console.log(form)
          editUser(form).then(response => {
            this.$message({
              message: '提交成功',
              type: 'success'
            })
            this.fetchData()
            this.formVisible = false
          })
        } else {
          return false
        }
      })
    },
    removeItem(record) {
      this.selRow = record
      this.remove()
    },
    remove() {
      if (this.checkSel()) {
        var id = this.selRow.id
        console.log(this.selRow)
        this.$confirm('确定删除该记录?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          remove(id).then(response => {
            this.$message({
              message: '删除成功',
              type: 'success'
            })
            this.fetchData()
          }).catch(err => {
            this.$notify.error({
              title: '错误',
              message: err
            })
          })
        }).catch(() => {})
      }
    },
    // 图片验证
    beforeImgUpload(file, type) {
      if (type == 'avatar') {
        this.avatarloading = true
      } else {
        this.backImgloading = true
      }
      const isJPG = file.type === 'image/jpeg' || file.type === 'image/gif' || file.type === 'image/png' || file.type === 'image/jpg'
      const isLt4M = file.size / 1024 / 1024 / 1024 / 1024 < 4

      if (!isJPG) {
        console.log('上传头像图片格式不支持')
        this.$message.error('上传头像图片格式不支持')
      }
      if (!isLt4M) {
        console.log('上传头像图片大小不能超过 4MB!')
        this.$message.error('上传头像图片大小不能超过 4MB!')
      }
      console.log(isJPG && isLt4M)
      return isJPG && isLt4M
    },
    // 上传头像成功处理逻辑
    handleAvatarSuccess(res) {
      console.log('res:' + res)
      if (res.code == 200) {
        this.form.avatar = res.data
        this.$notify({
          title: '成功',
          message: '头像上传成功',
          type: 'success'
        })
        this.avatarloading = false
      } else {
        this.$message({
          message: res.message,
          type: 'error'
        })
        this.avatarloading = false
      }
    },
    // 上传背景成功处理逻辑
    handleBackImgSuccess(res) {
      if (res.code == 0) {
        this.form.backImg = res.data
        this.$notify({
          title: '成功',
          message: '头像上传成功',
          type: 'success'
        })
        this.backImgloading = false
      } else {
        this.$message({
          message: res.message,
          type: 'error'
        })
        this.backImgloading = false
      }
    },
    resetPwd() {
      if (this.checkSel()) {
        var id = this.selRow.id
        this.$confirm('将密码重置为maiqu?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          resetPassword(id).then(response => {
            this.$message({
              message: '重置密码成功',
              type: 'success'
            })
          }).catch(err => {
            this.$notify.error({
              title: '错误',
              message: err
            })
          })
        }).catch(() => {})
      }
    },
    openRoleItem(record) {
      this.selRow = record
      this.openRole()
    },
    openRole() {
      if (this.checkSel()) {
        this.roleDialog.visible = true
      }
    },
    setRole() {
      var checkedRoleKeys = this.$refs.roleTree.getCheckedKeys()
      var roleIds = ''
      for (var index in checkedRoleKeys) {
        roleIds += checkedRoleKeys[index] + ','
      }
      var data = {
        userId: this.selRow.id,
        roleIds: roleIds.slice(0, -1)
      }
      setRole(data).then(response => {
        this.roleDialog.visible = false
        this.fetchData()
        this.$message({
          message: '提交成功',
          type: 'success'
        })
      })
    }
  },
  computed: {
    token() {
      return {
        Authorization: getToken()
      }
    }
  }
}
