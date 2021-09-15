<template>
  <nav class="navbar navbar-expand-lg navbar-white bg-light">
    <div class="container">
      <button
        class="navbar-toggler"
        type="button"
        data-toggle="collapse"
        data-target="#navbarNav"
        aria-controls="navbarNav"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav">
          <li class="nav-item">
            <router-link class="nav-link" to="/home">Anasayfa</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" to="/about">Hakkımızda</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" v-if="isProjectSelected" to="/board"
              >Panel</router-link
            >
            <router-link class="nav-link" v-else to="/projects"
              >Panel</router-link
            >
          </li>
          <li class="nav-item" v-if="isLoggedIn" @click="goToProjects">
            <router-link class="nav-link" to="/projects">Projeler</router-link>
          </li>
        </ul>
      </div>
      <div
        v-if="!isLoggedIn"
        class="collapse navbar-collapse justify-content-end"
      >
        <ul class="navbar-nav">
          <li class="nav-item navbar-right">
            <router-link class="nav-link pr-3" to="/login"
              >Giriş Yap</router-link
            >
          </li>
          <li class="nav-item navbar-right">
            <router-link class="nav-link" to="/signup">Kayıt ol</router-link>
          </li>
        </ul>
      </div>
      <div v-if="isLoggedIn" class="navbar-collapse justify-content">
        <b-dropdown
          id="dropdown"
          :text="project ? project.projectName : 'Proje Seçiniz'"
          class="m-md-3"
          variant="lite"
        >
          <b-dropdown-item
            v-for="p in myProjects"
            :key="p.id"
            @click="selectProject(p)"
            href="/board"
          >
            {{ p.projectName }}
            <span v-if="project">
              <span v-if="project.id == p.id"> ✔ </span>
            </span>
          </b-dropdown-item>
          <b-dropdown-item
            @click="$router.push('/addProject')"
            >Proje Oluştur
          </b-dropdown-item>
        </b-dropdown>
      </div>
      <div v-if="isLoggedIn" class="navbar-collapse justify-content-end">
        <avatar :username="account.name" :size="30"></avatar>
        <b-dropdown
          id="dropdown-1"
          :text="account.name"
          variant="lite"
          class="m-md-2"
        >
          <b-dropdown-item @click="goToProfile">Profil</b-dropdown-item>
          <b-dropdown-item @click="logout">Çıkış Yap</b-dropdown-item>
        </b-dropdown>
      </div>
    </div>
  </nav>
</template>

<script>
import Avatar from "vue-avatar";

import { mapGetters } from "vuex";
import axios from "axios";
import Vue from "vue";
import VueToastr2 from "vue-toastr-2";
import "vue-toastr-2/dist/vue-toastr-2.min.css";

window.toastr = require("toastr");

Vue.use(VueToastr2);
export default {
  components: { Avatar },
  computed: {
    ...mapGetters("Auth", ["isLoggedIn", "account"]),
    ...mapGetters("Project", ["project", "isProjectSelected", "myProjects"]),
  },
  mounted() {
    this.goToProjects();
  },
  methods: {
    logout() {
      this.$store.dispatch("Auth/logout");
    },
    goToProfile() {
      this.$router.push("/profile");
    },
    selectProject(project) {
      this.$store.commit("Project/setProject", project);
    },
    goToProjects() {
      if (this.isLoggedIn) {
        this.$store.dispatch("Project/removeMyProject");
        this.$store.dispatch("Project/getMyProjects", this.account.id);
      }
    },
  },
};
</script>

<style scoped>
a {
  color: black;
  font-weight: bold;
}
a:hover {
  transition: ease 0.5s;
  color: tomato;
}
a.active {
  color: #0275d8;
  border-radius: 4px;
}
</style>