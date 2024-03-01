
<template>
  <Navbar />
  <div class="container">
        <div class="row mt-5">
          <div class="col col-6 justify-content-center align-items-center">
              <div class = "card">
                  <div class = "top">
                      <p>Arrastra aquí tus imágenes</p>
                  </div>
                  <div class="drag-area" @dragover.prevent="onDragover" @dragleave.prevent="onDragLeave" @drop.prevent="onDrop">
                    <span v-if="!isDragging">
                      Arrastra aquí tu imagen o
                      <span class="select" role="button" @click="selectFiles">
                        Elige
                      </span>
                    </span>
                    <div v-else  class="select">Deja la imagen aquí</div>
                    <input name="file" type="file" class="file" ref="fileInput" multiple @change="onFileSelect"/>
                  </div>
                  <div class="container">
                      <div class="image" v-for="(image,index) in images" :key="index">
                        <span class="delete" @click = "deleteImage(index)">&times;</span>
                        <img :src="image.url"/>
                      </div>
                  </div>
                  <button type = "button">Subir</button>
              </div>  
          </div>
          <div class="col col-6 justify-content-center align-items-center">
            <!--Aquí va la columna derecha-->
          </div> 
        </div>
           
        
    </div>
</template>

<script>
export default(await import('vue')).defineComponent({
  data(){
    return{
      images: [],
      isDragging: false,  
    }
  },
  methods:{
    selectFiles(){
      this.$refs.fileInput.click();
    },
    onFileSelect(event){
      const files = event.target.files;
      if(files.length === 0)return;
        for(let i = 0; i<files.length; i++){
          if(files[i].type.split('/')[0] !== 'image') continue;
          if(!this.images.some((e)=> e.name === files[i].name)){
            this.images.push({name: files[i].name, url: URL.createObjectURL(files[i])});
          }
        }
      
    },
    deleteImage(index){
      this.images.splice(index, 1);
    },
    onDragover(event){
      event.preventDefault();
      this.isDragging = true;
      event.dataTransfer.dropEffect = 'copy';
    },
    onDragLeave(event){
      event.preventDefault();
      this.isDragging = false;
    },
    onDrop(event){
      event.preventDefault();
      this.isDragging = false;
      const files = event.dataTransfer.files;
      for(let i = 0; i<files.length; i++){
          if(files[i].type.split('/')[0] !== 'image') continue;
          if(!this.images.some((e)=> e.name === files[i].name)){
            this.images.push({name: files[i].name, url: URL.createObjectURL(files[i])});
          }
        }
    }
  }
})
</script>

<style scoped>
.card{
  width: 100%;
  padding: 10%;
  box-shadow: 0 0 3px #142e50;
  border-radius: 10px;
  overflow: hidden;
}

.card .top{
  text-align: center;
}

.card p{
  font-weight: bold;
  color:#142e50
}

.card button{
  outline:0;
  border:0;
  color:rgb(241, 249, 255);
  border-radius: 4px;
  font-weight: 400;
  padding: 8px 13px;
  width: 100%;
  background: #142e50;

}

.card .drag-area{
  height:150px;
  border-radius:5px;
  border: 2px dashed #142e50;
  background: #f3f8ff;
  color:#142e50;
  display:flex;
  justify-content: center;
  align-items: center;
  user-select: center;
  -webkit-user-select: none;
  margin-top: 10px;
}

.card .drag-area .visible{
  font-size: 18px;
}

.card .select{
  color: #3f1db8;
  cursor: pointer;
  margin-left: 5px;
  cursor: pointer;
  transition: 0.4s;
}

.card .select:hover{
  opacity: 0.6;
}

.card .container{
  width: 100%;
  height: auto;
  display: flex;
  justify-content: flex-start;
  align-items: flex-start;
  flex-wrap: wrap;
  max-height: 200px;
  position: relative;
  margin-bottom:8px;
  padding-top: 15px;
}
.card .container .image{
  width:75px;
  margin-right: 5px;
  height: 75px;
  position: relative;
  margin-bottom: 8px;
}

.card .container .image img{
  width:100%;
  height: 100%;
  border-radius: 5px;
}

.card .container .image span{
  position: absolute;
  top: -2px;
  right: 9px;
  font-size: 20px;
  cursor: pointer;
}

.card input, 
.card .drag-area .on-drop,

.card .drag-area.dragover .visible{
  display: none;
}

.delete{
  z-index: 999;
  color:#ff0000;
  font-weight: bold;
  font-size: 30px;
}
</style>
