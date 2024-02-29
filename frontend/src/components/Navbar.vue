<template>
<!-- Navbar -->
<nav class="navbar navbar-expand-lg navbar-custom">
  <!-- Container wrapper -->
  <div class="container-fluid">
    <!-- Toggle button -->
    <button
      data-mdb-collapse-init
      class="navbar-toggler"
      type="button"
      data-mdb-target="#navbarSupportedContent"
      aria-controls="navbarSupportedContent"
      aria-expanded="false"
      aria-label="Toggle navigation"
    >
      <i class="fas fa-bars"></i>
    </button>

    <!-- Collapsible wrapper -->
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <!-- Navbar brand -->
      <a class="navbar-brand mt-2 mt-lg-0" href="/">
        <img
          src="/images/LogoMonoColor.png"
          height="35"
          alt="Cohabify"
          loading="lazy"
        />
      </a>
      <!-- Left links -->
      
      <!-- Left links -->
    </div>
    <!-- Collapsible wrapper -->

    <!-- Right elements -->
    <div class="d-flex align-items-center">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item">
          <a class="nav-link" href="#">
            <span class="badge rounded-pill badge-notification bg-danger">1</span>
            Chat
          </a>
        </li>
        <li class="nav-item">
          <a class="nav-link " href="#">Planes</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="#">Publicar Anuncio</a>
        </li>
      </ul>
      <!-- Icon -->
      <a class="text-reset me-3" href="#">
        <i class="fas fa-shopping-cart"></i>
      </a>

      <!-- Avatar -->
      <div class="dropdown">
        <a
          class="dropdown-toggle d-flex align-items-center hidden-arrow"
          href="#"
          id="navbarDropdownMenuAvatar"
          role="button"
          data-bs-toggle="dropdown"
          aria-expanded="false"
        >
          <img
            src="/images/user.png"
            class="rounded-circle"
            height="40"
            alt="Black and White Portrait of a Man"
            loading="lazy"
          />
        </a>
        <ul
          class="dropdown-menu dropdown-menu-end"
          aria-labelledby="navbarDropdownMenuAvatar"
        >
          <li>
            <a class="dropdown-item " href="#" >Perfil</a>
          </li>
          <li>
            <a class="dropdown-item" href="#">Cerrar sesión</a>
          </li>
        </ul>
      </div>
    </div>
    <!-- Right elements -->
  </div>
  <!-- Container wrapper -->
</nav>
<!-- Navbar -->
</template>

<style scoped>
 .nav-link {
   color: white;
   transition: 0.1s;
 }

 .nav-link:hover {
   color: #a4c7ff;
   transition: 0.2s;
 }
 .dropdown-toggle::after {
  border-top-color: white; /* Cambia esto al color que prefieras */
  border-width: 0.3em; /* Cambia esto al tamaño que prefieras */
}

.dropdown-toggle:hover::after {
  border-top-color: #a4c7ff; /* Cambia esto al color que prefieras */
  transition: 0.2s;
}
</style>
