<template>
  <el-dialog
    :title="!dataForm.attrGroupId ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="100px">
    <el-form-item label="组名" prop="attrGroupName">
      <el-input v-model="dataForm.attrGroupName" placeholder="组名"></el-input>
    </el-form-item>
    <el-form-item label="排序" prop="sort">
      <el-input v-model="dataForm.sort" placeholder="排序"></el-input>
    </el-form-item>
    <el-form-item label="描述" prop="descript">
      <el-input v-model="dataForm.descript" placeholder="描述"></el-input>
    </el-form-item>
    <el-form-item label="组图标" prop="icon">
      <el-upload
        class="upload-demo"
        :action="uploadUrl"
        name="file"
        :on-success="uploadSuccess"
        :file-list="fileList"
        list-type="picture">
        <el-button size="small" type="primary">点击上传</el-button>
      </el-upload>
    </el-form-item>
    <el-form-item label="所属分类id" prop="catelogId">
     <el-cascader
    v-model="dataForm.catelogIds"
    :options="categorys"
    :props="props"
    @change="handleChange">
    </el-cascader>
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        uploadUrl: 'http://localhost:3000/api/product/attrgroup/attrGroupIconUpload',
        props: {
          value: "catId",
          label: "name",
          children: "categoryChilds"
        },
        fileList: [],
        categorys: [],
        dataForm: {
          attrGroupId: 0,
          attrGroupName: '',
          sort: '',
          descript: '',
          icon: '',
          catelogIds: [],
          catelogId: 0
        },
        dataRule: {
          attrGroupName: [
            { required: true, message: '组名不能为空', trigger: 'blur' }
          ],
          sort: [
            { required: true, message: '排序不能为空', trigger: 'blur' }
          ],
          descript: [
            { required: true, message: '描述不能为空', trigger: 'blur' }
          ],
          icon: [
            { required: true, message: '组图标不能为空', trigger: 'blur' }
          ],
          catelogId: [
            { required: true, message: '所属分类id不能为空', trigger: 'blur' }
          ]
        }
      }
    },



  created(){
    this.requestCategoryListTree()
  },




    methods: {


    // 请求三级菜单
    requestCategoryListTree(){
      this.$http({
        url: this.$http.adornUrl("/product/category/list/categoryList/tree"),
        methods: "get",
      }).then(response => {

        if (response.data.code >= 10000 && response.data.code < 20000){
          this.$message.success(response.data.msg)
          this.categorys = response.data.categoryList
        }else{
          this.$message.error(response.data.msg)
        }
      })
    },


      // 属性分组的图标上传成功
      uploadSuccess(response){
        
        if (response.code >= 10000 && response.code < 20000){
          this.$message.success(response.msg)
          this.dataForm.icon = response.fullUrl
        }else{
          this.$message.error(response.msg)
        }
      },





      init (id) {
        this.dataForm.attrGroupId = id || 0
        this.visible = true
        this.dataForm.catelogIds = []

        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.attrGroupId) {
            this.$http({
              url: this.$http.adornUrl(`/product/attrgroup/info/${this.dataForm.attrGroupId}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code >= 10000 && data.code < 20000) {
                this.$message.success(data.msg)
                this.dataForm = data.attrGroup
 
              }else{
                this.$message.error(data.msg)
          }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        console.log(this.dataForm)
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/product/attrgroup/${!this.dataForm.attrGroupId ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'attrGroupId': this.dataForm.attrGroupId || undefined,
                'attrGroupName': this.dataForm.attrGroupName,
                'sort': this.dataForm.sort,
                'descript': this.dataForm.descript,
                'icon': this.dataForm.icon,
                'catelogId': this.dataForm.catelogIds[this.dataForm.catelogIds.length -1]
              })
            }).then(({data}) => {
              if (data && data.code >= 10000 && data.code < 20000) {
                this.$message({
                  message: data.msg,
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
                this.fileList = []
                this.dataForm = {}
              } else {
                this.$message.error(data.msg)
              }

            })
          }
        })
      }
    }
  }
</script>
