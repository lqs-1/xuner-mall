<template>
  <el-tree
    :data="categoryListTree"
    :props="categoryListProps"
    @node-click="nodeClick"
    node-key="catId">
  </el-tree>
</template>

<script>
export default {
  name: "category",
  data(){
    return{
      categoryListTree: [],
      categoryListProps: {
        children: "categoryChilds",
        label: "name"
      }
    }
  },

  created() {
    this.requestCategoryListTree()
  },


  methods:{
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


    // 节点被点击,就把节点信息发送给父节点
    nodeClick(data, node, component){
      // 将节点信息发送给父节点
      this.$emit("tree-node-click", data, node, component)
    }
  }
}
</script>

<style scoped>

</style>
