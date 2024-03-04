<script>
import { ref, onMounted,onBeforeMount } from 'vue';
import {Popover} from 'bootstrap';
export default {
    
    setup() {

        const tags= ref([{tag: 'Próximamente...',tagtype:"USER_TAG"},{tag: 'Próximamente...',tagtype:"USER_TAG"},
        {tag: 'Próximamente...',tagtype:"USER_TAG"},{tag: 'Próximamente...',tagtype:"USER_TAG"}]);
        
        const userAd = ref({
            title: '',
            description: '',
            maxBudget: '',
            desiredLocation: '',
            entranceDate: '',
            exitDate: '',
            maxCohabitants: '',
        });
        const popoverContent = ref('');

        const handleClick = (tag) => {
            
        };

        const initializePopoverContent = () => {
            popoverContent.value = `<div style="width: 100%; display: block;">
                <p>Vuelve a pulsar el botón para cerrar...</p>`
            for (const tag of tags.value){
                 popoverContent.value += `
                <button type="button" class="btn btn-primary" @click.prevent="handleClick(tag)">`+ tag.tag +`</button>`
            }
            popoverContent.value += `</div>`;
        };

        const saveUserAd = async () => {
            
            try{
            const response = await fetch(import.meta.env.VITE_BACKEND_URL + '/api/userAdvertisement', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(userAd.value),
            });
            const data = await response.json();
            window.location.href = '/'
            } catch (error) {
                console.error('Error:', error); 
            }
        };

        onBeforeMount(() => {
            initializePopoverContent();
        });

        onMounted(() => {
            const popoverTriggerList = document.querySelectorAll('[data-bs-toggle="popover"]');
            const popoverList = [...popoverTriggerList].map(popoverTriggerEl => new Popover(popoverTriggerEl,{
                html: true,
                sanitize: false,
            }));
        });


        return{
            tags,
            handleClick,
            popoverContent,
            userAd,
            saveUserAd,
            
        }
    }
}
</script>

<template>
<Navbar/>
<div class="contenedor d-flex align-items-center justify-content-center text-center">
    <div class="panel">
        <div class="columna" style="flex-grow:2">
            <form>
                <div class="form-group" style="text-align: left; margin-top: 3vh;">
                    <input type="text" class="form-control" id="titulo" v-model="userAd.title" placeholder="Añadir título..."> 
                    <h5>Presupuesto</h5>
                    <div class="input-group mb-3 d-flex w-50">
                        <div class="d-flex align-items-center">
                            <input type="number" class="form-control" aria-label="Cantidad máxima" v-model="userAd.maxBudget" placeholder="Max...">
                            <div class="input-group-append">
                                <span class="input-group-text">€</span>
                            </div>
                        </div>
                    </div>
                    <div class="input-group mb-3 d-flex w-50">
                        <input type="text" class="form-control" aria-label="Añadir ubicación" v-model="userAd.desiredLocation" placeholder="Añadir ubicación...">
                        <div class="input-group-append">
                            <span class="input-group-text"><i class="bi bi-geo-alt" style="font-size: inherit;"></i></span>
                        </div>
                    </div>
                    <h5>Estancia</h5>
                    <div class="input-group mb-3 d-flex w-75 align-items-center">
                        <div class="input-up">
                            <label for="entrada">Entrada</label>
                            <div class="d-flex align-items-center">
                                <input type="date" id="entrada" class="form-control" style="margin-right: 1vw;" aria-label="Cantidad mínima" v-model="userAd.entranceDate" placeholder="Min...">
                                <h5 style="margin: 0 2vw"> - </h5>
                            </div>
                        </div>
                        <div class="input-up">
                            <label for="salida">Salida</label>
                           <input type="date" id="salida" class="form-control" style="margin-left: 1vw;" aria-label="Cantidad máxima" v-model="userAd.exitDate" placeholder="Max...">
                        </div>
                    </div>
                    <h5>Inquilinos</h5>
                    <div class="input-group mb-3 d-flex w-75 align-items-center">
                        <div class="input-up">
                            <label for="entrada">Número</label>
                            <div class="d-flex align-items-center">
                                <input type="number" id="entrada" class="form-control" aria-label="Cantidad máxima" v-model="userAd.maxCohabitants" placeholder="Max..."> 
                            </div>
                        </div>
                    </div>
                    <div class="input-up">
                        <h5>Etiquetas</h5>
                        <button type="button" class="btn btn-secondary mb-3" data-bs-container="body" data-bs-toggle="popover" data-bs-placement="bottom" :data-bs-content="popoverContent" >
                            +
                        </button>
                    </div>
                    <textarea class="form-control" id="descripcion" rows="3" v-model="userAd.description" placeholder="Añadir descripción..."></textarea>
                </div>
                <div class=mt-3>
                    <button type="submit" class="btn btn-primary" @click.prevent="saveUserAd">Guardar</button>
                </div>
            </form>
        </div>
    </div>
</div>

</template>

<style>
.contenedor {
    position: relative;
    display: flex;
    height: auto;
    width: 97%;
    margin: 1%;
    justify-content: start;
    align-items: start;
}

.panel {
    width: 60vw;
    max-width: 100%;
}

.columna {
    display: flex;
    height: auto;
    flex-direction: column;
    flex: 1;
    overflow-y: auto;
    overflow-x: hidden;
    margin-bottom: 5px;
    align-items: left;
}

.input-up {
    display: flex;
    flex-direction: column;
    align-items: flex-start;
}
h5 {
    margin: 1vh 0 !important;   
}

.popover{
    --bs-popover-max-width: 80% !important;
}



</style>
