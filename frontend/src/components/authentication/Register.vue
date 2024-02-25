<template>
    <div class="mx-auto p-2" style="width: 70%;">
        <img src="/images/LogoCohabify.png" class="img-fluid rounded-start" alt="..." style="max-width: 400px; padding-bottom: 2%;">
        <h1 style="padding-bottom: 30px;">Registro</h1>
        <div class="card">
            <form class="row gy-2 gx-3 align-items-center">
                <div class="col-auto">
                    <label class="form-label" for="autoSizingInput">Nombre completo</label>
                    <input type="text" class="form-control" id="autoSizingInput" placeholder="Jane Doe">
                </div>
                <div class="col-auto">
                    <label class="visually-hidden" for="autoSizingInputGroup">Username</label>
                    <div class="input-group">
                    <div class="input-group-text">@</div>
                    <input type="text" class="form-control" id="autoSizingInputGroup" placeholder="Username">
                    </div>
                </div>
                <div class="col-auto">
                    <label class="visually-hidden" for="autoSizingSelect">Preference</label>
                    <select class="form-select" id="autoSizingSelect">
                    <option selected>Choose...</option>
                    <option value="1">One</option>
                    <option value="2">Two</option>
                    <option value="3">Three</option>
                    </select>
                </div>
                <div class="col-auto">
                    <div class="form-check">
                    <input class="form-check-input" type="checkbox" id="autoSizingCheck">
                    <label class="form-check-label" for="autoSizingCheck">
                        Remember me
                    </label>
                    </div>
                </div>
                <div class="col-auto">
                    <button type="submit" class="btn btn-primary">Submit</button>
                </div>
            </form>
        </div>
    </div>
</template>

<script>
import { ref } from 'vue'

export default {
    setup() {
        const username = ref('')
        const email = ref('')
        const password = ref('')

        const register = () => {
            // Lógica de registro aquí
        }

        return {
            username,
            email,
            password,
            register
        }
    }
}
</script>

<style scoped>

.card {
    padding-top: 40px;
    padding-bottom: 40px;
    padding-left: 40px;
    padding-right: 40px;
    border: 1px  #28426b30;
    border-radius: 4px;
    background-color: #28426b9d;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

</style>


