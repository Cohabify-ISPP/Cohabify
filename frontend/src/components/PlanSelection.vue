<script>
    import { ref, onMounted, computed, watch } from 'vue'
    import { useStore } from 'vuex'
    import { useRouter } from 'vue-router'
    import { loadStripe } from '@stripe/stripe-js';

    export default {

        setup() {
            const store = useStore()
            const currentUser = computed(() => store.state.user);
            const router = useRouter();
            const plan = computed(() => currentUser.value.plan);
            const loggedIn = ref(false);
            const stripePromise = loadStripe('' + import.meta.env.VITE_STRIPE_PUBLISHABLE_KEY);
            const loading = ref(false);
            const lineItems = ref(null);
            const isLoading = ref(true);

            const updateMeta = (title, description) => {
            document.querySelector('meta[name="description"]').setAttribute('content', description);
            document.querySelector('meta[property="og:title"]').setAttribute('content', title);
            document.querySelector('meta[property="og:description"]').setAttribute('content', description);
            };

            onMounted(() => {
                updateMeta('Escoge tu plan en Cohabify', 'Selecciona entre Básico, Explorador, y Propietario para maximizar tu experiencia en Cohabify.');
            });

            onMounted(async () => {
            
            const urlParams = new URLSearchParams(window.location.search);
            const sessionId = urlParams.get('session_id');
            if (sessionId !== null) {
                const response = await fetch(import.meta.env.VITE_BACKEND_URL + '/api/stripe/session', {
                    method: 'POST',
                    headers: {
                        'Authentication': 'Bearer ' + localStorage.getItem("authentication"),
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({ sessionId: sessionId })
                }).then(response => {
                    if (response.status === 200) {
                        return response.json();
                    } else { 
                        throw new Error('Error al cargar el plan del usuario');
                    }
                    }) 
                    .then(jsonData => {
                        changePlan(jsonData.plan)
                        isLoading.value = false
                    })
                    .catch(error => {
                        isLoading.value = false
                        fetchError.value = error
                    })
            }else{
                isLoading.value = false
            }
                    });

            const handleCheckout = async (newPlan) => {
                if (await confirmChangePlan()){
                    if (newPlan === 'basic') {
                        changePlan(newPlan)
                    } else if (newPlan === 'explorer') {
                        lineItems.value = [{ price: 'price_1P2GsuBofFRUNSKsf2qfzvZr', quantity: 1}];
                    } else if (newPlan === 'owner') {
                        lineItems.value = [{ price: 'price_1P2WH6BofFRUNSKs24h67005', quantity: 1}];
                    }
                    if (lineItems.value !== null) {
                        const stripe = await stripePromise;
                        const { error } = await stripe.redirectToCheckout({
                            lineItems: lineItems.value,
                            mode: 'payment',
                            successUrl: window.location.origin+'/plan?session_id={CHECKOUT_SESSION_ID}',
                            cancelUrl: window.location.origin,
                        });

                        if (error) {
                            console.error(error);
                        }
                    } 
                }

            } ;

            const changePlan = async (newPlan) => {
                    const response = await fetch(
                        import.meta.env.VITE_BACKEND_URL + '/api/user/update/plan/'+ newPlan + '/'+currentUser.value.id,
                        {
                            method: 'PUT',
                            headers: {
                                'Authentication': 'Bearer ' + localStorage.getItem('authentication')
                            },
                        }
                    )
                    store.dispatch('cargarUser')
            } 
            
            const confirmChangePlan = async () => {
                var response = confirm("¿Estás seguro de que quieres cambiar de plan?");
                return response;
            }

            watch(currentUser, (newValue) => {
                if (newValue !== null && newValue !== undefined && Object.keys(newValue).length !== 0) { 
                    loggedIn.value = true;
                }
            });
        
            return { 
            currentUser,
            plan,
            changePlan,
            loggedIn,
            loading,
            handleCheckout,
            isLoading,
            }
        }
    }

</script>

<template>
     <navbar />
     <h1 style="margin-top: 1vw; margin-bottom: 2vw;">Escoge tu plan</h1>
     <div v-if="isLoading" class="spinner-border mt-5" role="status">
                    <span class="visually-hidden">Loading...</span>
    </div>
    <div v-else class="container" style="padding:0 12vw ;">
            
    <div  class="row d-flex justify-content-center align-items-start  vh-80" >
            <div class="col">
                <div class="card card1">
                    <h2 class="fw-bold" style="color: #28426B;">Básico</h2>
                    <p class="fw-bold"><em>Para nuevos usuarios</em></p>
                    <div class="modal-body">
                        
                            <img src="/images/Check.png" style="height: 20px; width: 20px;"></img><span > Ver publicaciones ilimitadas</span>
                            <br>
                            <br>
                            <img src="/images/Check.png" style="height: 20px; width: 20px;"><span > 3 chats en paralelo</span>
                            <br>
                            <br>
                            <img src="/images/Check.png" style="height: 20px; width: 20px;"><span > Perfil personalizado</span>
                            <br>
                            <br>
                            <img src="/images/Check.png" style="height: 20px; width: 20px;"><span > Hacer publicación</span>
                    
                        <div style="text-align: center;">
                            <h2 class="fw-bold" style="padding-top: 1vw;color: #28426B;"> Gratis</h2>
                            <div v-if="!loggedIn">
                                <button class="btn-plan" style="margin-top: 1vw;" >Inicia sesión para contratar un plan</button>
                            </div>
                            <div v-else>
                                <button class="btn-primary" style="margin-top: 1vw;" v-if="plan !== 'basic'" @click="handleCheckout('basic')">¡Lo quiero!</button>
                                <button class="btn-plan" style="margin-top: 1vw;" v-else>¡Ya lo tengo!</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col" style="margin-left: 40px; margin-right: 40px;">
                <div class="card card2">
                    <h2 class="fw-bold"  style="color: #28426B;">Explorador</h2>
                    <p class="fw-bold"><em>Para los más exigentes</em></p>
                    <div class="modal-body">
                        
                            <img src="/images/Check.png" style="height: 20px; width: 20px;"><span > Ver publicaciones ilimitadas</span>
                            <br>
                            <br>
                            <img src="/images/Check.png" style="height: 20px; width: 20px;"><span class="fw-bold" > Chats ilimitados</span>
                            <br>
                            <br>
                            <img src="/images/Check.png" style="height: 20px; width: 20px;"><span class="fw-bold"> Perfil verificado</span>
                            <br>
                            <br>
                            <img src="/images/Check.png" style="height: 20px; width: 20px;"><span class="fw-bold"> Acceso anticipado*</span>
                    
                        <div style="text-align: center;">
                            <h2 class="fw-bold" style="padding-top: 1vw;color: #28426B;"> 5€</h2>
                            <div v-if="!loggedIn">
                                <button class="btn-plan" style="margin-top: 1vw;" >Inicia sesión para contratar un plan</button>
                            </div>
                            <div v-else>
                                <button class="btn-primary" style="margin-top: 1vw;" v-if="plan !== 'explorer'" @click="handleCheckout('explorer')">¡Lo quiero!</button>
                                <button class="btn-plan" style="margin-top: 1vw;" v-else>¡Ya lo tengo!</button>
                            </div>
                        </div>
                    </div>
                </div>
                <p style="margin-top:1vw"><em>* El plan explorador podrá ver las nuevas publicaciones con 24 horas de antelación. El plan básico sólo podrá ver 
                            publicaciones con más de un día de antigüedad
                </em></p>
            </div>
            <div class="col">
                <div class="card card3">
                    <h2 class="fw-bold" style="color: #28426B;">Propietario</h2>
                    <p class="fw-bold"><em>Para nuestros propietarios</em></p>
                    <div class="modal-body">
                       
                            <img src="/images/Check.png" style="height: 20px; width: 20px;"><span class="fw-bold"> Publicar viviendas</span>
                            <br>
                            <br>
                            <br>
                            <img src="/images/Check.png" style="height: 20px; width: 20px;"><span class="fw-bold" > Chats ilimitados</span>
                            <br>
                            <br>
                            <img src="/images/Check.png" style="height: 20px; width: 20px;"><span class="fw-bold"> Estadísticas</span>
                            <br>
                            <br>
                            <br style="height: 20px; width: 20px;">
                            
                    
                        <div style="text-align: center;">
                            <h2 class="fw-bold" style="padding-top: 1vw;color: #28426B;"> 15€ + 5€*</h2>
                            <div v-if="!loggedIn">
                                <button class="btn-plan" style="margin-top: 1vw;" >Inicia sesión para contratar un plan</button>
                            </div>
                            <div v-else>
                                <button class="btn-primary" style="margin-top: 1vw;" v-if="plan !== 'owner'" @click="handleCheckout('owner')">¡Lo quiero!</button>
                                <button class="btn-plan" style="margin-top: 1vw;" v-else>¡Ya lo tengo!</button>
                            </div>
                        </div>
                    </div>
                </div>
                <p style="margin-top:1vw"><em>* El plan propietario debe pagar 5€ por cada propiedad adicional que desee publicar</em></p>
            </div>
    </div>
    <div class="row mb-3">
        <div class="col card card4">
            <h3 class="fw-bold" style="color: #28426B;">Promociones</h3>
            <p class="fw-bold"><em>Promociona tus anuncios de vivienda o tu anuncio como compañero. Durante 24 horas el anuncio que quieras aparecerá en 
                lo más alto del listado de anuncios, permitiendo así que más personas puedan ver tu publicación.</em></p>

            <div style="text-align: center;">
                <h2 class="fw-bold" style="color: #28426B;"> 3€</h2>
            </div>

        </div>
    </div>
    </div>
   
</template>

<style scoped>

.modal-body {
    padding: 30px 15px;
    text-align: left;
}
.card {
    padding-top: 20px;
    padding-bottom: 20px;
    padding-left: 20px;
    padding-right: 20px;
    border: 1px  #28426b30;
    border-radius: 20px;
    box-shadow: 10px 10px 4px rgba(0, 0, 0, 0.1);
    transition: transform 0.2s;
}

.card:hover {
    transform: scale(1.05);
}

.card1 {
    background-color: #DAE5FF;
}

.card2 {
    background-color: #BFD3FF;
}

.card3 {
    background-color: #98B2E8;
}

.card4 {
    background-color: #d2dfff;

}

input {
    margin-bottom: 10px;
    padding: 8px;
    border: 1px solid #ccc;
    border-radius: 4px;
}

button {
    border: none;
    border-radius: 4px;
    cursor: pointer;
}


</style>
