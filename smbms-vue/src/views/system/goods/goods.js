import {
  getList,
  saveGoods,
  remove,
  editGoods
} from '@/api/system/goods'
import {
  getToken
} from '@/utils/auth'

export default {
  name: 'mgr',
  data() {
    return {
      options: [{
        value: '选项1',
        label: '小于10元'
      }, {
        value: '选项2',
        label: '10-50元'
      }, {
        value: '选项3',
        label: '50-100元'
      }, {
        value: '选项4',
        label: '100元以上'
      }],
      value: '',
      price: '',
      editDialog: {
        visible: false,
        title: '修改商品'
      },
      formVisible: false,
      formTitle: '添加用户',
      roleSet: [],
      form: {
        name: '',
        price: '',
        comment: '',
        brand: '',
        providerId: '',
        stock: '',
        coverPic: ''
      },
      listQuery: {
        name: null,
        brand: null,
        upperPrice: null,
        lowerPrice: null,
        page: 1,
        limit: 5
      },
      coverPicloading: false,
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
    // 获取商品信息
    fetchData() {
      this.listLoading = true
      getList(this.listQuery).then(response => {
        this.list = response.data.dataCurrentPage
        this.listLoading = false
        this.total = response.data.totalCount
      })
    },
    // 获取价格信息
    getPrice() {
      var self = this

      if (self.price === '选项1') {
        self.listQuery.upperPrice = 10
        self.listQuery.lowerPrice = 0
      } else if (self.price === '选项2') {
        self.listQuery.upperPrice = 50
        self.listQuery.lowerPrice = 10
      } else if (self.price === '选项3') {
        self.listQuery.upperPrice = 100
        self.listQuery.lowerPrice = 50
      } else if (self.price === '选项4') {
        self.listQuery.upperPrice = 10000000
        self.listQuery.lowerPrice = 100
      }
    },
    // 搜索
    search() {
      this.listQuery.page = 1
      this.getPrice()
      this.fetchData()
    },
    // 重置
    reset() {
      this.listQuery.name = null
      this.listQuery.brand = null
      this.listQuery.upperPrice = null
      this.listQuery.lowerPrice = null
      this.listQuery.page = 1
      this.listQuery.limit = 5
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
        name: '',
        price: '',
        comment: '',
        brand: '',
        providerId: '',
        stock: '',
        coverPic: ''
      }
    },
    add() {
      this.resetForm()
      this.formTitle = '添加商品'
      this.formVisible = true
    },
    saveGoods() {
      var self = this
      this.$refs['form'].validate((valid) => {
        if (valid) {
          var form = self.form
          saveGoods(form).then(response => {
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
        const newForm = {
          id: form.id,
          name: form.name,
          price: form.price,
          comment: form.comment,
          brand: form.brand,
          providerId: form.providerId,
          stock: form.stock,
          coverPic: form.coverPic
        }
        this.form = newForm
        this.editDialog.title = '修改商品'
        this.editDialog.visible = true
      }
    },
    editGoods() {
      var self = this
      this.$refs['form'].validate((valid) => {
        if (valid) {
          var form = self.form
          console.log(form)
          editGoods(form).then(response => {
            this.$message({
              message: '提交成功',
              type: 'success'
            })
            this.fetchData()
            this.editDialog.visible = false
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
    },
    // 图片验证
    beforeImgUpload(file, type) {
      if (type == 'coverPic') {
        this.coverPicloading = true
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
      console.log('is' + isJPG && isLt4M)
      return isJPG && isLt4M
    },
    // 上传封面图成功处理逻辑
    handleCoverPicSuccess(res) {
      console.log('res:' + res)
      if (res.code == 200) {
        this.form.coverPic = res.data
        this.$notify({
          title: '成功',
          message: '封面图上传成功',
          type: 'success'
        })
        this.coverPicloading = false
      } else {
        this.$message({
          message: res.message,
          type: 'error'
        })
        this.coverPicloading = false
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
