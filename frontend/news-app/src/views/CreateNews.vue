<template>
  <div class="pt-5">
    <form method="post" @submit.prevent="postNews()" >
      <div class="form-group">
        <label for="title">Title</label>
        <input style="margin-top: 10px;" v-model="title" type="text" class="form-control" id="title" placeholder="Enter title">
      </div>
      <div class="form-group">
        <label for="content" style="margin-top: 10px;">Content</label>
        <textarea style="margin-top: 10px;" cols="40" rows="5" v-model="content" type="text" class="form-control" id="content" placeholder="Enter content">
        </textarea>
      </div>
      <div class="form-group">
          <label for="tags">Tags</label>
          <input style="margin-top: 10px;" v-model="tags" type="text" class="form-control" id="tags" placeholder="Enter tags">
      </div>
      <br>
      <div class = "row"  style="text-align: center">
        <div class="col form-group">
          <b-form-select v-model = "selectedCategory" class="m-3">
            <b-form-select-option  v-for="category in categories" :key="category.name" :value= "category" >{{category.name}}</b-form-select-option>
          </b-form-select>
        </div>
      </div>
      <br><br><br>
      <div style="text-align: center">
        <button  type="submit" class="btn btn-primary">Publish News</button>
      </div>
   </form>
    <br>  <br>
    <table class=" table text-center" style="width: 650px;margin-left: 300px;">
      <thead>
        <tr>
          <!--            <th scope="col">ID</th>-->
          <th scope="col">Title</th>
          <th scope="col">Created</th>
          <th scope="col">Content</th>
          <th scope="col">Delete</th>
          <th scope="col">Edit</th>
        </tr>
      </thead>

      <tbody >
      <tr v-for="news in newsList" :key="news.id" >
        <b-card style="margin-top: 10px">
          <td scope="row" @click="find(news.id)"> {{ news.title }}</td>
        </b-card>
        <td>{{ (new Date(news.createdAt)).toLocaleDateString("en-us", { weekday: "long", year: "numeric", month: "short", day: "numeric",}) }}</td>
        <td>{{ news.content | shortText }}</td>
        <td scope="row">
          <b-button v-if="newsList.length > 1" @click="deleteNews(news.id)" size="sm">Delete</b-button>
        </td>
        <td scope="row">
          <b-button @click="editNews(news.id)" size="sm">Edit</b-button>
        </td>
      </tr>
      </tbody>

    </table>

  </div>
</template>

<script>

import jwt_decode from 'jwt-decode';

export default {
  name: "CreateNews",
  filters: {
    shortText(value) {
      if (value.length < 30) {
        return value;
      }
      return value.slice(0, 30) + '...'
    }
  },
  data() {
    return {
      date: '',
      selectedCategory: null,
      title: null,
      content: null,
      tags: null,
      categories: [],
      newsList: []
    }
  },
  mounted() {
    this.$axios.get('/api/categories?page=1').then((response) => {
      this.categories = response.data;
    });
    this.$axios.get('/api/news?page=1').then((response) => {
      this.newsList = response.data;
    });
  },
  methods: {
    deleteNews(id) {
      this.$axios.delete(`/api/news/${id}`).then(() => {
        
      });
      window.location.reload()
    },
    find(id) {
      this.$router.push(`/news/single-news-view/${id}`);
    },
    editNews(id) {
      this.$router.push(`/news/${id}`)
    },
    postNews(){
      const jwt = localStorage.getItem('jwt');
      if (jwt === null){
          return
      }

      if(!this.validateInputs()){
          alert('invalid inputs')
          return
      }

      const decoded = jwt_decode(jwt);

      this.$axios.post(`/api/news/insert`, {
        "categoryId": this.selectedCategory.id,
        "title": this.title,
        "content": this.content,
        "author": decoded.firstname + " " + decoded.lastname,
        "createdAt": Date.now(),
        "tags": this.tags

      }).then(() => {
        window.location.reload();
      });
    },

    validateInputs(){
        return this.selectedCategory && this.title && this.content && this.title !== '' && this.content !== ''
    }
  }
}
</script>

<style scoped>

</style>
