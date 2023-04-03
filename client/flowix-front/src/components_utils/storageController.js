
import axios from "axios";
import router from "../rout/router";

export default {
    data() {
        return {
            username: "",
            token: "",
            role: "",
            mapAdmin: [],
            mapToken: []
        }
    },

    methods: {
        async loginUser(telegramId){
            await axios.post("http://localhost:8000/auth/loginAdmin", {
                telegramId: telegramId,
                // hashUser: hash,
                // authDateUser: auth_date
            })
                .then(response => {
                    this.mapAdmin = response.data;
                    localStorage.username = this.mapAdmin.usernameAuth;
                    localStorage.token = this.mapAdmin.tokenAdmin;
                    localStorage.role = this.mapAdmin.roleAdmin;
                })
                .catch(e => {
                    console.log(e.response.status, " asdsda")
                    if(e.response.status === 403){
                        router.push("/registrationAdmin")
                    }
                })

        },

        async updateAccess(){
            await axios.post("http://localhost:8000/tools/update_access", {
                username: localStorage.username,
                role: localStorage.role
            })
                .then(response => {
                    this.mapToken = response.data;
                    localStorage.token = this.mapToken.accessToken})
        }
    }
}

