<template>
<h1>Admin</h1>
</template>

<script>
import storageController from "../components_utils/storageController";
import router from "../rout/router";

const api = axios.create({
  baseURL: "http://localhost:8000",
});

api.interceptors.response.use(null, error => {
  if(error.response.status === 401) {
    storageController.methods.updateAccess();
    api.get("/auth/admin")
        .then(response => {
          console.log(response.data);
          this.info = response.data
        });
  }
  if(error.response.status === 405) {
    router.push("/loginAdmin");
  }
  })

api.interceptors.request.use(config => {
  alert("im here")
  config.headers = {'Authorization': 'Bearer_'+ localStorage.token};
  return config
})

import axios from "axios"
export default {
  name: "AdminComponent",
  data(){
    return {
      info: []
    }
  },
  methods: {
    getInformation() {
      return api.get("/auth/admin")
          .then(response => {
            console.log(response.data);
            this.info = response.data
          });
    }
  },
  created() {
    console.log(localStorage.token);
    api.get("/auth/admin")
        .then(response => {
          console.log(response.data);
          this.info = response.data
        });
  }

}
</script>

<style scoped>

</style>