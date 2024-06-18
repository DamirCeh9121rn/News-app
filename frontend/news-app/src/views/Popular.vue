<template>
  <div class="news" style="text-align: center">
    <h1 class="mt-4">Popular</h1>

    <div class="row" style="display:inline;">
      <div class="col-4 mx-auto" >
        <table class=" table text-center" style="width: 800px;margin-left: -250px;">
          <thead>
          <tr>
            <th scope="col">Title</th>
            <th scope="col">Visits</th>
            <th scope="col">Created</th>
            <th scope="col">Content</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="news in newsList" :key="news.id" @click="find(news.id)">

            <b-card style="margin-top: 10px">
            <td scope="row">{{ news.title }}</td>
            </b-card>
            <td>{{ news.visits }}</td>
            <td>{{ (new Date(news.createdAt)).toLocaleDateString("en-us", { weekday: "long", year: "numeric", month: "short", day: "numeric",})}}</td>
            <b-card style="margin-top: 10px">
            <td>{{ news.content | shortText }}</td>
            </b-card>

          </tr>
          </tbody>
        </table>
      </div>
      <div class="col-6">
        <NewsDetails v-if="selectedNews" :news="selectedNews"></NewsDetails>
      </div>
    </div>
  </div>
</template>


<script>
import NewsDetails from "./NewsDetails.vue";

export default {

  components: {NewsDetails},
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
      selectedNews: null,
      newsList: []
    }
  },
  methods: {
    find(id) {
      this.$router.push(`/news/single-news-view/${id}`);
    }
  },
  mounted() {
    this.$axios.get('/api/news/most-read').then((response) => {
      this.newsList = response.data;
    });
  }
}
</script>
