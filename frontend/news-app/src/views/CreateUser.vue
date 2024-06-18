<template>
  <div class="pt-5">
    <form method="post" v-on:submit.prevent = "postUser()" >
      <div class="form-group">
        <label for="email">Email</label>
        <input style="margin-top: 10px;" required  v-model="email" type="email" class="form-control" id="email" placeholder="Enter email">
      </div>
      <br>
      <div class="form-group">
        <label for="name" style="margin-top: 10px;">Name</label>
        <input style="margin-top: 10px;" required v-model="firstname" type="name" class="form-control" id="name" placeholder="Enter name">

      </div>
      <br>
      <div class="form-group">
        <label for="surname" style="margin-top: 10px;">Surname</label>
        <input style="margin-top: 10px;" required v-model="lastname" type="lastname" class="form-control" id="surname" placeholder="Enter surname">
      </div>
      <br>
      <div class="form-group">
        <label for="password" style="margin-top: 10px;">Password</label>
        <input style="margin-top: 10px;" required  v-model="password" type="password" class="form-control" id="password" placeholder="Enter password">
      </div>
      <div class="form-group">
        <label for="confirmPassword" style="margin-top: 10px;">Confirm Password</label>
        <input style="margin-top: 10px;" required  v-model="confirmPassword" type="password" class="form-control" id="confirmPassword" placeholder="Repeat password">
      </div>
      <br>
      <div class="form-group">
        <label for="isAdmin" style="margin-top: 10px;">Is Administrator</label>
        <input style="margin-top: 10px;" required  v-model="isAdmin" type="number" class="form-control" id="isAdmin" placeholder="Is Administrator">
      </div>
      <br>
      <div class="form-group">
          <label for="status" style="margin-top: 10px;">Is active</label>
          <input style="margin-top: 10px;" required  v-model="status" type="number" class="form-control" id="status" placeholder="Is active">
      </div>
      <br>

      <button type="submit" class="btn btn-primary mt-2">Add User</button>
    </form>

    <h1 class="mt-4">Users</h1>
    <div class="row" style="display:inline;">
      <div class="col-4 mx-auto" >
        <table class=" table text-center" style="width: 650px;margin-left: -150px;">
          <thead>
          <tr>
            <th scope="col">Email</th>
            <th scope="col">Name</th>
            <th scope="col">Last Name</th>
            <th scope="col">Is Admin</th>
            <th scope="col">Is Active</th>
            <th scope="col">Edit</th>
            <th scope="col">Change Status</th>
          </tr>
          </thead>
          <tbody >
          <tr v-for="user in users" :key="user.email" >
            <b-card style="margin-top: 10px">
              <td scope="row"> {{ user.email }}</td>
            </b-card>
            <td scope="row"> {{ user.firstname }}</td>
            <td scope="row"> {{ user.lastname }}</td>
            <td scope="row"> {{ user.role }}</td>
            <td scope="row"> {{ user.status }}</td>
            <td scope="row">
              <b-button @click="editUser(user.id)" size="sm">Edit</b-button>
            </td>
            <td scope="row" v-if="isAdminLoggedIn">
              <b-button @click="userActivation(user.id)" size="sm">Activate</b-button>
            </td>
            <td scope="row" v-if="isAdminLoggedIn">
              <b-button @click="userDeactivation(user.id)" size="sm">Deactivate</b-button>
            </td>
          </tr>

          </tbody>
        </table>
      </div>

    </div>

  </div>
</template>

<script>

import jwtDecode from "jwt-decode";

export default {
  name: "CreateUser",
  data() {
    return {
      date: '',
      email: null,
      firstname: null,
      lastname: null,
      password: null,
      confirmPassword: null,
      status: false,
      isAdmin: null,
      users: [],
    }
  },
  mounted() {
    this.$axios.get('/api/users/get-all?page=1').then((response) => {
      this.users = response.data;
    });
  },
  computed: {
      // eslint-disable-next-line vue/no-dupe-keys
      isAdminLoggedIn() {
          const jwt = localStorage.getItem('jwt');
          if (jwt === null)
              return false;

          const decoded = jwtDecode(jwt);
          return decoded.role === 'admin';
      }
  },
  methods: {
    editUser(id) {
      this.$router.push(`/user/update/${id}`);
    },
    postUser() {
      if (this.password !== this.confirmPassword){
        alert('passwords does not match')
        return;
      }

      if(!this.validateInputs()){
        alert('invalid inputs')
        return
      }

      this.$axios.post('/api/users/register', {
        "firstname": this.firstname,
        "lastname": this.lastname,
        "email": this.email,
        "password": this.password,
        "isAdmin": this.isAdmin === 'true' || this.isAdmin === '1',
        "status": this.status === 'true' || this.status === '1'

      }).then(() => {
          window.location.reload()
      }).catch(() => {
          alert("Email je zauzet")
      })
    },
    userActivation(id){
      this.$axios.put(`/api/users/status-activation/${id}`).then(() => {
        window.location.reload()
      })
    },
    userDeactivation(id){
        this.$axios.put(`/api/users/status-deactivation/${id}`).then(() => {
            window.location.reload()
        })
    },
    validateInputs(){
      return this.firstname && this.lastname && this.email && this.password && this.firstname !== '' && this.lastname !== '' && this.email !== '' && this.password !== ''
    }
  }
}




</script>

<style scoped>

</style>
