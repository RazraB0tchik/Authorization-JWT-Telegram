<template>
  <div class="container-fluid">
    <div class="row d-flex justify-content-center">
      <div class= "col-xl-6 col-md-8">
        <div class="d-flex justify-content-center align-items-center" style="width: 100%; height: 100vh">
          <div class="window_registration d-flex align-items-center flex-column justify-content-around">
            <div>Hello Admin</div>
                        <ButtonLogin
                            mode="callback"
                            bot-username="FlowixAdminBot"
                            size="large"
                            @callback='callbackFunction'/>
<!--            <button @click="startEl">Click!</button>-->
          </div>
        </div>
      </div>
    </div>
<!--  <div class="container-fluid h-50" style="background-color: blueviolet;">-->
<!--    <div class="row h-100">-->
<!--      <div class="col" style="background-color: orange; text-align: center">-->

<!--        <div class="w-50 h-100" style="min-width: 300px; background-color: cadetblue">-->
<!--            <div class="mx-auto text-center h-25 w-25" style="background-color: brown">-->
<!--              Login-->
<!--            </div>-->
<!--            <ButtonLogin-->
<!--                mode="callback"-->
<!--                bot-username="FlowixAdminBot"-->
<!--                size="large"-->
<!--                @callback='callbackFunction'/>-->
<!--        </div>-->
<!--        </div>-->
<!--      </div>-->
<!--    </div>-->
  </div>
</template>

<script setup>
import {ButtonLogin} from 'televue';
import storageController from "@/components_utils/storageController";

    const callbackFunction = (user) =>{
      console.log(user)
      storageController.methods.loginUser(user.id);
    }
//     const callbackFunctionTwo = () =>{
//   storageController.methods.loginUser(23423422);
// }
</script>
<style scoped>
@import "../fonts/fonst.css";

.window_registration{
  width: 80%;
  height: 50%;
  border-style: solid;
  border-color: #4183E4;
  border-radius: 10%;
  border-width: 10px;
  font-size: 5vw;
  font-family: Minecraft_Classic,serif;
}

</style>






<!--<template>-->
<!--  <div class="d-flex align-items-center justify-content-center" style="background-color: palegreen; width: 100%; height: 100vh">-->
<!--    <div class="container-fluid h-50" style="background-color: blueviolet;">-->
<!--      <div class="row h-100">-->
<!--        <div class="col" style="background-color: orange; text-align: center">-->

<!--          <div class="w-50 h-100" style="min-width: 300px; background-color: cadetblue">-->
<!--            <div class="mx-auto text-center h-25 w-25" style="background-color: brown">-->
<!--              Login-->
<!--            </div>-->
<!--            <ButtonLogin-->
<!--                mode="callback"-->
<!--                bot-username="FlowixAdminBot"-->
<!--                size="large"-->
<!--                @callback='callbackFunction'/>-->
<!--          </div>-->
<!--        </div>-->
<!--      </div>-->
<!--    </div>-->
<!--  </div>-->
<!--</template>-->

<!--<script setup>-->
<!--import {ButtonLogin} from 'televue';-->

<!--const callbackFunction = (user) => {-->
<!--  console.log(user)-->
<!--}-->

<!--</script>-->
<!--<style scoped>-->
<!--</style>-->



