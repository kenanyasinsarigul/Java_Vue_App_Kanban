<template>
  <div class="container mt-4">
    <b-row>
      <b-col>
        <b-col style="float: left">
          <router-link
            to="/addProject"
            tag="button"
            class="btn btn-primary"
            id="addProject"
          >
            Proje Oluştur +
          </router-link>
        </b-col>
        <div class="justify-content-md-center">
          <div class="card" style="margin-top: 5%">
            <div class="card-body">
              <div class="table-responsive" style="overflow-x: hidden">
                <table class="table" style="margin-left: 0.5rem">
                  <thead>
                    <tr class="row">
                      <th class="col-sm-1">SAHİPLİK</th>
                      <th class="col-sm-3">PROJE ADI</th>
                      <th class="col-sm-3">ÜYELER</th>
                      <th class="col-sm-5">AKSİYONLAR</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr
                      class="row"
                      v-for="(project, index) in myProjects"
                      v-bind:key="project.id"
                    >
                      <td class="col-sm-1">
                        <span
                          class="badge bg-warning"
                          v-if="project.ownerId != account.id"
                          >Üye</span
                        >
                        <span
                          class="badge bg-dark"
                          v-if="project.ownerId == account.id"
                          >Yönetici</span
                        >
                      </td>
                      <td class="prjname col-sm-3">
                        {{ project.projectName }}
                      </td>
                      <td class="col-sm-3">
                        <div
                          class="pull-left"
                          v-for="member in project.members"
                          v-bind:key="member.id"
                        >
                          <avatar :username="member.name" :size="30"></avatar>
                        </div>
                      </td>
                      <td class="col-sm-5">
                        <button
                          @click="selectProject(project)"
                          type="button"
                          class="btn btn-success"
                        >
                         Proje Seç
                        </button>

                        &nbsp;
                        <router-link
                          :to="'/updateProject/' + project.id"
                          v-if="project.ownerId == account.id"
                        >
                          <button type="button" class="btn btn-primary">
                           Proje Güncelle
                          </button>
                        </router-link>
                        &nbsp;
                        <a
                          href="#"
                          class="icon"
                          v-if="project.ownerId == account.id"
                        >
                          <button
                            v-on:click="onDeleteProject(project.id, index)"
                            type="button"
                            class="btn btn-danger"
                          >
                            Proje Sil
                          </button>

                          <b-dropdown
                            id="dropdown"
                            :text="'Üye Sil'"
                            class="m-md-3 dropdown"
                            variant="danger"
                          >
                            <b-dropdown-item
                              v-for="member in project.members"
                              :key="member.id"
                              @click="onDeleteMember(project.id, member.id)"
                            >
                              {{ member.name }}
                              
                            </b-dropdown-item>
                            
                          </b-dropdown>
                        </a>
                      </td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </div>
      </b-col>
    </b-row>
  </div>
</template>

<script>
import Avatar from "vue-avatar";
import { mapGetters } from "vuex";
import axios from "axios";
export default {
  components: { Avatar },
  data() {
    return {
      editId: "",
      ownerId: null,
      projects: [],
    };
  },
  computed: {
    ...mapGetters("Auth", ["account", "jwt"]),
    ...mapGetters("Project", ["project", "myProjects"]),
  },
  mounted() {
    this.ownerId = this.account.id;
    this.getprojects();
  },
  methods: {
    getprojects() {
      this.$store.dispatch("Project/removeMyProject");
      this.$store.dispatch("Project/getMyProjects", this.account.id);
    },
    onDeleteProject(id, index) {
      if (!confirm("Silinsin mi?")) {
        return;
      }
      axios
        .delete(`/projects/${id}`)
        .then((response) => {
          this.$toastr.info("Proje silindi.", "Başarılı!");
          this.myProjects.splice(index, 1);
          if (id == this.project.id) {
            this.$store.commit("Project/setProject", null);
          }
          this.$store.dispatch("Project/getMyProjects", this.account.id);
        })
        .catch((error) => {});
    },
   async onDeleteMember(projectId, memberId) {
      if (!confirm("Silinsin mi?")) {
        return;
      }
      var payload = {
        projectId: projectId,
        memberId: memberId,
      };
      await this.$store.dispatch("Project/removeByMember", payload);
      this.getprojects();
      this.$toastr.info("Üye silindi.", "Başarılı!");
      
    },
    selectProject(project) {
      this.$store.commit("Project/setProject", project);
      this.getprojects();
      this.$router.push("/board");
    },
  },
};
</script>

<style scoped>
#addProject {
  float: right;
}
.badge {
  margin-right: 10px;
}
.prjname {
  font-weight: bold;
}
.dropdown {
  position: relative;
  display: inline-block;
  text-decoration: none;
}
</style>
