<script>
import { ref, onMounted, computed, watch } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import { loadStripe } from '@stripe/stripe-js';

export default{

    setup(){

      const checkboxError = ref(false);
      const checkboxValue = ref(false);
        const router = useRouter();
        
        const updateMeta = (title, description) => {
        document.querySelector('meta[name="description"]').setAttribute('content', description);
        document.querySelector('meta[property="og:title"]').setAttribute('content', title);
        document.querySelector('meta[property="og:description"]').setAttribute('content', description);
        };

        onMounted(() => {
            updateMeta('Eliminar Cuenta - Cohabify', 'Confirme si desea eliminar su cuenta permanentemente en Cohabify.');
        });
        const confirmAction = ()=> {
          if(!checkboxValue.value){
            checkboxError.value = true;
            return;
          }
            router.push("/");
        }

        return {
            checkboxValue,
            checkboxError,
            confirmAction
        }
    }

}


</script>

<template>
    <Navbar/>
  <div class="right-to-erasure d-flex flex-column justify-content-center align-items-center" style="margin-top:5%;">
    <h2>Eliminar Cuenta</h2>
    <p>
      Desea borrar su cuenta? Esta acción no podrá deshacerse y si se confirma su solicitud todos sus datos serán borrados.
    </p>
    <div class="d-flex">
      <input type="checkbox" id="confirmation" v-model="checkboxValue">Confirmo que quiero eliminar mi cuenta.
    </div>
    <div class="mt-3 alert alert-danger" role="alert" style="padding: 2%;" v-if="checkboxError">
      <p style="margin: 0"><i class="fas fa-exclamation-triangle"></i>  Debe confirmar que quiere eliminar la cuenta. </p>
    </div>
    <button type="submit" @click="confirmAction" style="margin-top: 5%;">Confirmar</button>
  </div>
</template>


<style scoped>
.right-to-erasure {
  max-width: 400px;
  margin: 0 auto;

}

button {
  padding: 10px 20px;
  background-color: #28426b;;
  color: #fff;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

button:active {
  background-color: #3d5c8e;
}
</style>
