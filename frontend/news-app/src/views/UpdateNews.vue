<template>
  <div class="pt-5">
    <h2 class="mt-4">Edit News</h2>
    <br>

        <form method="post" v-on:submit.prevent = "editNews()" >
          <div class="form-group">
            <label for="title">Title</label>
            <input style="margin-top: 10px;" v-model="title" v-text="title" type="text" class="form-control" id="title" placeholder="Enter title">
          </div>
          <div class="form-group">
            <label for="content" style="margin-top: 10px;">Content</label>
            <textarea style="margin-top: 10px;" cols="40" rows="5" v-model="content" v-text="content" type="text" class="form-control" id="content" placeholder="Enter content"/>
          </div>
          <div class="form-group">
              <label for="content" style="margin-top: 10px;">Tags</label>
              <textarea style="margin-top: 10px;" cols="40" rows="5" v-model="tags" v-text="tags" type="text" class="form-control" id="tags" placeholder="Enter tags"/>
          </div>
          <br>
          <div class = "row"  style="text-align: center">
            <div class="col form-group">
              <b-form-select v-model = "selectedCategory"  class="m-3">
                <b-form-select-option  v-for="category in categories" :key="category.name" :value= "category" >{{category.name}}</b-form-select-option>
              </b-form-select>
            </div>
          </div>
          <br>  <br>
          <br>
          <div style="text-align: center">
            <button  type="submit" class="btn btn-primary">Publish News</button>
          </div>
        </form>
        <br>
        <br>
      </div>
</template>
<script>

export default {
  name: "UpdateNews",
  data() {
    return {
      title: null,
      content: null,
      tags: null,
      categoryId: null,
      categories: [],
      selectedCategory: null,
      tag: null
    }
  },
  mounted() {
    this.$axios.get(`/api/news/${this.$route.params.id}`)
        .then((response) => {
            this.title = response.data.title;
            this.content = response.data.content;
            this.categoryId = response.data.categoryId;
            this.tags = response.data.tags
        })
        .then(() => {
            this.$axios.get('/api/categories?page=1').then((response) => {
                this.categories = response.data;
                for (const category of this.categories) {
                    if (category.id === this.categoryId){
                        this.selectedCategory = category;
                    }
                }
            });
        })
  },
  methods: {
    editNews() {
      this.$axios.put(`/api/news/update/${this.$route.params.id}`, {
        "title": this.title,
        "content": this.content,
        "tags": this.tags,
        "categoryId": this.selectedCategory.id
      }).then(() => {
        this.$router.push(`/news`);
      });
    }
  }
}
</script>

<style scoped>

</style>
