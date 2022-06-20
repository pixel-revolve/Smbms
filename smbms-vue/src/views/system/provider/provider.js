import {
  getList,
  saveProvider,
  remove,
  editProvider
} from '@/api/system/provider'
import {
  getToken
} from '@/utils/auth'

export default {
  name: 'mgr',
  data() {
    return {
      editDialog: {
        visible: false,
        title: '修改供应商'
      },
      formVisible: false,
      formTitle: '添加用户',
      roleSet: [],
      form: {
        code: '',
        name: '',
        desc: '',
        phone: '',
        address: ''
      },
      listQuery: {
        code: '',
        name: '',
        desc: '',
        phone: '',
        address: '',
        page: 1,
        limit: 5
      },
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
    // 搜索
    search() {
      this.listQuery.page = 1
      this.fetchData()
    },
    // 重置
    reset() {
      this.listQuery.code = ''
      this.listQuery.name = ''
      this.listQuery.desc = ''
      this.listQuery.phone = ''
      this.listQuery.address = ''
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
        code: '',
        name: '',
        desc: '',
        phone: '',
        address: ''
      }
    },
    add() {
      this.resetForm()
      this.formTitle = '添加供应商'
      this.formVisible = true
    },
    saveProvider() {
      var self = this
      this.$refs['form'].validate((valid) => {
        if (valid) {
          var form = self.form
          console.log(form)
          saveProvider(form).then(response => {
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
          code: form.code,
          name: form.name,
          desc: form.desc,
          phone: form.phone,
          address: form.address
        }
        this.form = newForm
        this.editDialog.title = '修改供应商'
        this.editDialog.visible = true
      }
    },
    editProvider() {
      var self = this
      this.$refs['form'].validate((valid) => {
        if (valid) {
          var form = self.form
          console.log(form)
          editProvider(form).then(response => {
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
