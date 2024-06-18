<template>
  <div class="pt-5">
    <form method="post" v-on:submit.prevent = "editUser()" >
      <div class="form-group">
        <label for="email">Email</label>
        <input style="margin-top: 10px;"  v-model="email" v-text="email" type="email" class="form-control" id="email" >
      </div>
      <br>
      <div class="form-group">
        <label for="firstname" style="margin-top: 10px;">firstname</label>
        <input style="margin-top: 10px;"  v-model="firstname" v-text="firstname" type="text" class="form-control" id="firstname" placeholder="Enter firstname">
      </div>
      <br>
        <div class="form-group">
            <label for="email">Password</label>
            <input style="margin-top: 10px;"  v-model="password" v-text="password" type="text" class="form-control" id="password" >
        </div>
        <br>
      <div class="form-group">
        <label for="lastname" style="margin-top: 10px;">Lastname</label>
        <input style="margin-top: 10px;"  v-model="lastname" v-text="lastname" type="text" class="form-control" id="lastname" placeholder="Enter lastname">
      </div>
      <br>
      <br>
      <div class="form-group">
        <label for="isAdmin" style="margin-top: 10px;">Is Admin</label>
        <input style="margin-top: 10px;"  v-model="isAdmin" type="number" class="form-control" id="isAdmin" placeholder="Is Admin">
      </div>
      <div class="form-group">
          <label for="status" style="margin-top: 10px;">Status</label>
          <input style="margin-top: 10px;"  v-model="status" type="number" class="form-control" id="status" placeholder="status">
      </div>
      <br>

      <button type="submit" class="btn btn-primary mt-2">Update user</button>
    </form>
  </div>
</template>

<script>


export default {
  name: 'UpdateUser',
  data() {
    return {
      email: null,
      firstname: null,
      lastname: null,
      password: null,
      isAdmin: null,
      status: null,
    }
  },
  mounted() {
    this.$axios.get(`/api/users/by-id/${this.$route.params.id}`).then((response) => {
      this.email = response.data.email
      this.firstname = response.data.firstname
      this.lastname = response.data.lastname
      this.isAdmin = response.data.role === 'admin' ? '1' : '0'
      this.status = response.data.status ? '1' : '0'
    });
  },
  methods: {
    editUser(){
      this.$axios.put(`/api/users/update/${this.$route.params.id}`, {
        "email": this.email,
        "firstname": this.firstname,
        "lastname": this.lastname,
        "password": this.password,
        "role": (this.isAdmin === 'true' || this.isAdmin === '1') ? 'admin' : 'content_creator',
        "status": this.status === 'true' || this.status === '1'

      }).then(() => {
        this.$router.push(`/users`);
      })
    },
  }
}
</script>

<style scoped>

</style>
