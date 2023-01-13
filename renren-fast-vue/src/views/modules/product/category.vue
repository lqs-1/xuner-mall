<template>
  <div>
    <el-tree
      :data="categoryListTree"
      :props="categoryListProps"
      :expand-on-click-node="false"
      :default-expanded-keys="openMenu"
      show-checkbox
      node-key="catId">
      <span class="custom-tree-node" slot-scope="{ node, data }">
        <span>{{ node.label }}</span>
        <span>
          <el-button v-if="node.level <= 2" type="text" size="mini" @click="() => append(data)">Append</el-button>
          <el-button v-if="node.childNodes.length == 0" type="text" size="mini" @click="() => remove(node, data)">Delete</el-button>
          <el-button type="text" size="mini" @click="() => edit(node, data)">edit</el-button>
        </span>
      </span>
    </el-tree>

<!--    添加菜单对话框-->
    <el-dialog
      title="添加子菜单"
      :visible.sync="dialogVisible"
      width="30%">
      <span>
      <el-form :model="appendForm">
        <el-form-item label="子菜单">
         <el-input v-model="appendForm.name" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
        </span>
      <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false;appendForm.name = ''">取 消</el-button>
          <el-button type="primary" @click="appendCategory">确 定</el-button>
     </span>
    </el-dialog>


    <!--    修改菜单对话框-->
    <el-dialog
      title="修改菜单"
      :visible.sync="dialogEditVisible"
      width="30%">
      <span>
      <el-form :model="editForm">
        <el-form-item label="菜单名">
         <el-input v-model="editForm.name" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
        </span>
      <span slot="footer" class="dialog-footer">
          <el-button @click="dialogEditVisible = false;editForm.name = ''">取 消</el-button>
          <el-button type="primary" @click="editCategory">确 定</el-button>
     </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "category",
  data(){
    return {
      categoryListTree: [],
      openMenu: [],
      dialogVisible: false,
      dialogEditVisible: false,
      editForm: {
        name:"",
        catId: 0
      },
      appendForm: {
        name: "",
        parentCid: 0,
        catLevel: 0,
        showStatus: 1,
        sort: 0
      },
      categoryListProps: {
        children: "categoryChilds",
        label: "name"
      }
    }
  },

  created() {
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
          this.categoryListTree = response.data.categoryList
        }else{
          this.$message.error(response.data.msg)
        }
      })
    },

    // 添加三级分类
    appendCategory(){
      this.$http({
        url: this.$http.adornUrl("/product/category/add"),
        method: "post",
        data: this.$http.adornData(this.appendForm)
      }).then(response => {
        if (response.data.code >= 10000 && response.data.code < 20000){
          this.$message.success(response.data.msg)
          this.openMenu = [this.appendForm.parentCid]
          this.requestCategoryListTree()
        }else{
          this.$message.error(response.data.msg)
        }
      })
      this.dialogVisible = false
    },

    // 菜单添加
    append(data){
      // 弹出添加对话框
      this.appendForm.name = ""
      this.dialogVisible = true
      // 添加数据
      this.appendForm.parentCid = data.catId * 1
      this.appendForm.catLevel = data.catLevel *1 + 1
    },

    // 菜单删除
    remove(node, data){

      this.$confirm('是否删除' + data.name + '菜单', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http({
          url: this.$http.adornUrl("/product/category/delete"),
          method: "delete",
          data: this.$http.adornData([data.catId], false)
        }).then(response => {
          if (response.data.code >= 10000 && response.data.code < 20000){
            this.$message.success(response.data.msg)
            this.openMenu = [this.appendForm.parentCid]
            this.requestCategoryListTree()
          }else{
            this.$message.error(response.data.msg)
          }

        })
      }).catch(() => {
        this.$message.info("已取消删除")
      });
    },

    // 修改三级分类
    editCategory(){
      this.$http({
        url: this.$http.adornUrl("/product/category/update"),
        method: "put",
        data: this.$http.adornData(this.editForm)
      }).then(response => {

        if (response.data.code >= 10000 && response.data.code < 20000){
          this.$message.success(response.data.msg)
          this.requestCategoryListTree()
        }else{
          this.$message.error(response.data.msg)
        }
      })
      this.dialogEditVisible = false
    },

    // 菜单修改
    edit(node, data){
      // 显示修改菜单对话框
      this.editForm.name = ""
      this.dialogEditVisible = true
      this.editForm.catId = data.catId* 1
      this.openMenu = [data.parentCid]
    }
  }
}
</script>

<style scoped>

</style>
