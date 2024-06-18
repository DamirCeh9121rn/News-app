<template>
  <div class="pt-5">
    <form method="post" v-on:submit.prevent = "postCategory()" >
      <div class="form-group">
        <label for="name">Name</label>
        <input style="margin-top: 10px;" required  v-model="name" type="text" class="form-control" id="name" placeholder="Enter name">
      </div>
      <div class="form-group">
        <label for="description" style="margin-top: 10px;">Description</label>
        <input style="margin-top: 10px;" required  v-model="description" type="text" class="form-control" id="description" placeholder="Enter description">
      </div>
      <br>
      <button type="submit" class="btn btn-primary mt-2">Create Category</button>
    </form>

    <h1 class="mt-4">Categories</h1>
    <div class="row" style="display:inline;">
      <div class="col-4 mx-auto" >
        <table class=" table text-center" style="width: 650px;margin-left: -150px;">
          <thead>
          <tr>
            <th scope="col">Name</th>
            <th scope="col">Description</th>
            <th scope="col">Delete</th>
            <th scope="col">Edit</th>
          </tr>
          </thead>
          <tbody >
          <tr v-for="category in categories" :key="category.name">
            <b-card style="margin-top: 10px">
              <td scope="row" @click="findNewsByCategory(category.id)"> {{ category.name }}</td>
            </b-card>
            <td scope="row"> {{ category.description }}</td>
            <td scope="row">
              <b-button v-if="categories.length > 1" @click="deleteCategory(category.id)" size="sm">Delete</b-button>
            </td>
            <td scope="row">
              <b-button @click="editCategory(category.id)">Edit</b-button>
            </td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  name: "CreateCategory",
  data() {
    return {
      name: null,
      description: null,
      categories: [],
    }
  },
  mounted() {
    this.$axios.get('/api/categories?page=1').then((response) => {
      this.categories = response.data;
    });
  },
  methods: {
    editCategory(id) {
      this.$router.push(`/categories/${id}`)
    },
    deleteCategory(id) {
      this.$axios.delete(`/api/categories/${id}`).then(() => {

      });
      window.location.reload()
    },
    postCategory(){

      if(!this.validateInputs()){
          alert('invalid inputs')
          return
      }

      this.$axios.post('/api/categories/insert', {
        "name": this.name,
        "description": this.description
      }).then(() => {
        window.location.reload()
      })
    },
    findNewsByCategory(id) {
      this.$router.push(`/news/by-category/${id}`);
    },
    validateInputs(){
        return this.name && this.description && this.name !== '' && this.description !== ''
    }
  }
}
</script>

<style scoped>

</style>
