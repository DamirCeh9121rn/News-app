<template >
  <div>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">NEWS</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                  <li class="nav-item">
                      <router-link :to="{name: 'All-News'}" tag="a" class="nav-link" :class="{active: this.$router.currentRoute.name === 'News'}">News</router-link>
                  </li>
                  <li class="nav-item">
                      <router-link :to="{name: 'Popular'}" tag="a" class="nav-link" :class="{active: this.$router.currentRoute.name === 'Popular'}">Popular</router-link>
                  </li>
                    <b-input  v-model="wordSearch" placeholder="Search"></b-input>
                    <b-button @click="getNewsByWord" type="submit">Search</b-button>
                  <b-dropdown text="Categories" variant="secondary" class="e-auto mb-2 mb-lg-0 me-2" style="height: 35px; margin-top: 5px">
                      <b-dropdown-item href="#" v-for="category in categories" :key="category.name"  @click="getNewsByCategory(category.id)">{{category.name}}</b-dropdown-item>
                  </b-dropdown>
                  <li v-if="canLogin" class="nav-item">
                      <router-link :to="{name: 'Login'}" tag="a" class="nav-link" :class="{active: this.$router.currentRoute.name === 'Login'}">Login</router-link>
                  </li>
                  <b-dropdown v-if="canLogout" text="CMS" variant="secondary" class="e-auto mb-2 mb-lg-0" style="height: 35px; margin-top: 5px">
                      <b-dropdown-item>
                          <router-link :to="{name: 'CreateNews'}" tag="a" class="nav-link" :class="{active: this.$router.currentRoute.name === 'CreateNews'}">NEWS</router-link>
                      </b-dropdown-item>
                      <b-dropdown-item>
                          <router-link :to="{name: 'CreateCategory'}" tag="a" class="nav-link" :class="{active: this.$router.currentRoute.name === 'CreateCategory'}">CATEGORIES</router-link>
                      </b-dropdown-item>
                      <b-dropdown-item v-if="isAdmin">
                          <router-link :to="{name: 'CreateUser'}" tag="a" class="nav-link" :class="{active: this.$router.currentRoute.name === 'CreateUser'}">USERS</router-link>
                      </b-dropdown-item>
                  </b-dropdown>
                </ul>
                <form v-if="canLogout" class="d-flex" @submit.prevent="logout">
                    <button class="btn btn-outline-secondary" type="submit">Logout</button>
                </form>
            </div>
        </div>
    </nav>
  </div>
</template>

<script>

import jwtDecode from "jwt-decode";
export default {
    name: "NavBar",
    computed: {
        canLogin() {
            return localStorage.getItem('jwt') == null || localStorage.getItem('jwt') === '';
        },
        canLogout() {
            return this.$route.name !== 'Login' && localStorage.getItem('jwt') != null;
        },
        isAdmin() {
            const jwt = localStorage.getItem('jwt');
            if (jwt === null)
                return false;
            console.log()
            const decoded = jwtDecode(jwt);
            console.log(decoded.role)
            return decoded.role === 'admin';
        }
    },
    data() {
        return {
            selectedCategory: null,
            categories: [],
            wordSearch: null,
        }
    },
    mounted() {
        this.$axios.get('/api/categories?page=1').then((response) => {
            this.categories = response.data;
        });
    },
    methods: {
        logout() {
            localStorage.removeItem('jwt');
            this.$router.push({name: 'Login'});
            window.location.reload();
        },
        getNewsByCategory(id) {
            this.$router.push(`/news/by-category/${id}`).then(() => {
                window.location.reload();
            });
        },
        getNewsByWord() {
          let word = this.wordSearch;
          if (word !== undefined) {
            this.$router.push(`/news/by-word/${word}`).then(() => {
              window.location.reload();
            });
          }
        }
    }
}
</script>

<style scoped>

</style>