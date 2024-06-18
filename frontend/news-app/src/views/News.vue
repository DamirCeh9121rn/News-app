<template>
    <div class="news" style="text-align: center">
        <h1 class="mt-4">News</h1>

        <div class="row" style="display:inline;">
            <div class="col-4 mx-auto" >
                <table class=" table text-center" style="width: 650px;margin-left: -150px;">

                    <thead>
                    <tr>
                        <th scope="col">Title</th>
                        <th scope="col">Created At</th>
                        <th scope="col">Content</th>
                    </tr>
                    </thead>
                    <tbody >
                    <tr v-for="news in newsList" :key="news.id" @click="find(news.id)">
                        <b-card style="margin-top: 10px">
                            <td scope="row"> {{ news.title }}</td>
                        </b-card>
                        <td>{{(new Date(news.createdAt)).toLocaleDateString("en-us", { weekday: "long", year: "numeric", month: "short", day: "numeric",}) }}</td>
                        <td>{{ news.content | shortText }}</td>
                    </tr>
                    </tbody>
                </table>
                <ul class="pagination">
                    <li class="page-item"><a class="page-link" @click = "getPrevPageOfNews">Previous</a></li>
                    <li class="page-item"><a class="page-link">                      {{ this.pageNum }}</a></li>
                    <li class="page-item"><a class="page-link" @click = "getNextPageOfNews" >Next</a></li>
                </ul>
            </div>
        </div>
    </div>
</template>

<script>

export default {
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
            newsList: [],
            pageNum: 1
        }
    },
    mounted() {
        this.$axios.get('/api/news?page=1').then((response) => {
            this.newsList = response.data;
        });
    },
    methods: {
        find(id) {
            this.$router.push(`/news/single-news-view/${id}`);
        },
        getNextPageOfNews(){
            this.pageNum++
            this.$axios.get(`/api/news?page=${this.pageNum}`).then((response) => {
                this.newsList = response.data;
            });
        },
        getPrevPageOfNews(){
            if(this.pageNum === 1){
                return
            }
            this.pageNum--
            this.$axios.get(`/api/news?page=${this.pageNum}`).then((response) => {
                this.newsList = response.data;
            });
        }
    }
}
</script>

