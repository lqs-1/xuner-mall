<template>
<!--
使用说明：
1）、引入category-cascader.vue
2）、语法：<category-cascader :catelogPath.sync="catelogPath"></category-cascader>
    解释：
      catelogPath：指定的值是cascader初始化需要显示的值，应该和父组件的catelogPath绑定;
          由于有sync修饰符，所以cascader路径变化以后自动会修改父的catelogPath，这是结合子组件this.$emit("update:catelogPath",v);做的
      -->
  <div>
    <el-cascader
      filterable
      clearable
      v-model="paths"
      :options="categorys"
      :props="setting"
    ></el-cascader>
  </div>
</template>

<script>
import PubSub from 'pubsub-js'
export default {
  components: {},
  props: {
    catelogPath: {
      type: Array,
      default () {
        return []
      }
    }
  },
  data () {
    return {
      setting: {
        value: 'catId',
        label: 'name',
        children: 'categoryChilds'
      },
      categorys: [],
      paths: this.catelogPath
    }
  },
  watch:{
    catelogPath(v){
      this.paths = this.catelogPath;
    },
    paths(v){
      this.$emit("update:catelogPath",v);
      //还可以使用pubsub-js进行传值
      PubSub.publish("catPath",v);
    }
  },
  methods: {
    getCategorys () {
      this.$http({
        url: this.$http.adornUrl('/product/category/list/categoryList/tree'),
        method: 'get'
      }).then(({ data }) => {
        if (data.code >= 10000 && data.code < 20000){
          this.$message.success(data.msg)
          this.categorys = data.categoryList
        }else{
          this.$message.error(data.msg)
        }
      })
    }
  },
  created () {
    this.getCategorys()
  }
}
</script>