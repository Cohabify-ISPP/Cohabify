<script>
import { ref, onMounted } from 'vue';

export default {
    
    setup() {

        //props = ['id'];
        const userAdvertisement = ref({ description: "Desc de prueba", minBudget: 123.4, maxBudget: 567.8, desiredLocation: "Street 123", entranceDate: "2021-12-12", exitDate: "2022-12-12", maxCohabitants: 3});
        /*
        const fetchData = async () => {
            try {
                const response = await fetch(import.meta.env.VITE_API_URL + `/userAdvertisment/${id}`,
                    {
                        method: "GET",
                        credentials: "include",
                    });
                const data = await response.json();

                data.entranceDate = new Date(data.entranceDate);
                data.exitDate = new Date(data.exitDate);
                userAdvertisement.value = data;
            } catch (error) {
                console.error("Error:", error);
            }
        };
        */
        

        return {
            userAdvertisement,
        }
    }
}
</script>

<template>

    <Navbar />

    <div>
        <p>{{ userAdvertisement.description }}</p>
        <p>{{ userAdvertisement.minBudget }}</p>
        <p>{{ userAdvertisement.maxBudget }}</p>
        <p>{{ userAdvertisement.entranceDate }}</p>
        <p>{{ userAdvertisement.exitDate }}</p>
        <p>{{ userAdvertisement.maxCohabitants }}</p>
    </div>

</template>

<style scoped>

</style>