<template>
  <div class="pt-5">
    <h2 class="mt-4">Edit Category</h2>
    <br>

        <form method="post" v-on:submit.prevent = "editCategory()" >
          <div class="form-group">
            <label for="name">Name</label>
            <input style="margin-top: 10px;"  v-model="name" v-text="name" type="text" class="form-control" id="name" placeholder="Enter name">
          </div>
          <div class="form-group">
            <label for="description" style="margin-top: 10px;">Description</label>
            <input style="margin-top: 10px;"  v-model="description" v-text="description" type="text" class="form-control" id="description" placeholder="Enter description">

          </div>
          <br>

          <button type="submit" class="btn btn-primary mt-2">Update Category</button>
        </form>
      </div>
</template>
<script>

export default {
  name: "UpdateCategory",
  data() {
    return {
      name: null,
      description: null,
    }
  },
  mounted() {
    this.$axios.get(`/api/categories/categotyById/${this.$route.params.id}`).then((response) => {
      this.name = response.data.name;
      this.description = response.data.description;
    });
  },
  methods: {
    editCategory() {
      this.$axios.put(`/api/categories/${this.$route.params.id}`, {
        "name": this.name,
        "description": this.description
      }).then(() => {
        this.$router.push(`/categories`);
      });
    },
  }
}
</script>

<style scoped>

</style>
