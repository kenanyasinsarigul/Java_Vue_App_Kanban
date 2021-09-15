
<template>
  <!-- Update Task Modal -->

  <div>
    <b-modal
      size="xl"
      id="updateDeleteTask"
      hide-footer
      title="Task Güncelle"
      v-if="taskData"
    >
      <div class="row">
        <div class="col-6">
          <form @submit.prevent="updateTask">
            <div class="row">
              <div>
                <b-form-group
                  label="Task Başlığı"
                  label-for="title"
                  invalid-feedback="Boş Olamaz!"
                >
                  <b-form-input
                    class="mt-2 mb-2"
                    id="title"
                    v-model="taskData.title"
                    :type="'text'"
                    required
                  ></b-form-input>
                </b-form-group>
              </div>

              <div>
                <b-form-group
                  label="Açıklama"
                  label-for="description"
                  invalid-feedback="Boş Olamaz!"
                >
                  <b-form-textarea
                    class="mt-2 mb-2"
                    id="description"
                    v-model="taskData.description"
                    placeholder="Açıklama yazın..."
                    rows="3"
                    max-rows="6"
                    required
                  ></b-form-textarea>
                </b-form-group>
              </div>

              <div>
                <b-form-group
                  label="Öncelik"
                  label-for="priority"
                  invalid-feedback="Boş Olamaz!"
                >
                  <b-form-select
                    style="width: 100%; padding: 5px"
                    class="mt-2 mb-2"
                    id="priority"
                    v-model="taskData.priority"
                    :options="prt_options"
                    :type="'text'"
                    required
                  >
                  </b-form-select>
                </b-form-group>
              </div>

              <div>
                <b-form-group
                  label="Atanan"
                  label-for="assignee"
                  invalid-feedback="Boş Olamaz!"
                >
                  <b-form-select
                    style="width: 100%; padding: 5px"
                    class="mt-2 mb-2"
                    id="assignee"
                    v-model="taskData.assignee"
                    :options="members"
                    :type="'text'"
                    required
                  >
                  </b-form-select>
                </b-form-group>
              </div>

              <div>
                <b-form-group
                  label="Teslim Tarihi"
                  label-for="dueDate"
                  invalid-feedback="Boş Olamaz!"
                >
                  <b-form-datepicker
                    class="mt-2 mb-2"
                    id="dueDate"
                    :min="min"
                    required="required"
                    v-model="taskData.dueDate"
                  ></b-form-datepicker>
                </b-form-group>
              </div>

              <br />

              <div class="upd-dlt mt-2">
                <b-button type="submit" variant="success">Güncelle</b-button>
                <b-button class="dlt" @click="deleteTask" variant="danger"
                  >Sil</b-button
                >
              </div>
            </div>
          </form>
        </div>
        <div class="col-6">
          <form>
            <div class="row comment">
              <div>
                <b-form-group label="Yorum" label-for="description">
                  <b-form-textarea
                    class="mt-2 mb-2"
                    id="description"
                    v-model="commentText"
                    placeholder="Yorum yazın..."
                    rows="3"
                    max-rows="6"
                  ></b-form-textarea>
                </b-form-group>
                <b-button class="mt-2" variant="success" @click="commentCreate"
                  >Yorum ekle</b-button
                >
              </div>

              <div v-for="comment in taskData.comments" :key="comment.id">
                <div class="row mt-3">
                  <div>
                    <b-card bg-variant="light">
                      <b-card-text>
                        <div class="row">
                          <div class="col-1 mt-1">
                            <avatar
                              :username="comment.commentOwner.name"
                              :size="30"
                            ></avatar>
                          </div>
                          <div class="col-7 mt-2">
                          <h5>  {{ comment.commentOwner.name }} </h5>
                          </div>
                          <div class="col-4 mt-1">
                            {{ comment.at|date }}
                          </div>
                        </div>
                      </b-card-text>

                      <b-card-text>
                          <div
                            style="
                              max-witdh: 160px;
                              word-wrap: break-word;
                              wthite-space: normal;
                            "
                          >
                            {{ comment.comment }}
                          </div>
                          <div class="mt-1">
                            <div class="delete">
                              <b-button size=sm class="fa fa-trash"
                                variant="danger"
                                @click="commentDelete(comment)"
                                ></b-button
                              >
                            </div>
                          </div>
                   
                      </b-card-text>
                    </b-card>
                  </div>
                </div>
              </div>
            </div>
          </form>
        </div>
      </div>
    </b-modal>
  </div>
</template>

<script>
import axios from "axios";
import { mapGetters } from "vuex";
import Avatar from "vue-avatar";

export default {
  props: ["taskCatch", "project"],
  data() {
    return {
      members: [],
      comments: [],
      min: new Date(),
      commentText: null,
      taskData: {
        project: null,
        title: null,
        status: "TODO",
        description: null,
        priority: null,
        assignee: null,
        createdDate: new Date(),
        dueDate: null,
      },
      priority: null,
      prt_options: [
        { value: "HIGHT", text: "YÜKSEK" },
        { value: "MEDIUM", text: "ORTA" },
        { value: "LOW", text: "DÜŞÜK" },
      ],
    };
  },
  mounted() {
    this.getProjectMembers();
    this.taskData.project = this.project;
    this.taskData = this.taskCatch;
  },
  components: {
    Avatar,
  },
  computed: {
    ...mapGetters("Auth", ["account"]),
  },
  methods: {
    commentUpdate() {
      const comment = {
        commentOwner: {
          id: this.account.id,
        },
        comment: this.commentText,
        task: {
          id: this.taskCatch.id,
        },
      };
      axios.put(`/comment/`, comment).then((r) => {
        this.commentText = null;
        this.commentDataUpdate();
      });
    },
    commentDelete(comment) {
         if (!confirm("Silinsin mi?")) {
        return;
      }
      axios.delete(`/comment/${comment.id}`).then((r) => {
        this.commentDataUpdate();
      });
    },
    commentCreate() {
      const comment = {
        commentOwner: {
          id: this.account.id,
        },
        comment: this.commentText,
        task: {
          id: this.taskCatch.id,
        },
      };
      axios.post(`/comment/`, comment).then((r) => {
        this.commentText = null;
        this.commentDataUpdate();
      });
    },
    commentDataUpdate() {
      axios.get(`/task/id/${this.taskCatch.id}`).then((r) => {
        this.taskData = r.data;
      });
    },
    updateTask() {
      console.log(this.taskData)
      axios.put("/task", this.taskData).then((response) => {
         console.log(response)
        this.$toastr.success("Task güncellendi.", "Başarılı!");
        this.$emit("updated", response.data);
        this.$bvModal.hide("updateDeleteTask");
      });
    },
    deleteTask() {
      if (!confirm("Silinsin mi?")) {
        return;
      }
      axios.delete(`/task/${this.taskCatch.id}`).then((response) => {
        this.$toastr.info("Task silindi.", "Başarılı!");
        this.$emit("updated", response.data);
        this.$bvModal.hide("updateDeleteTask");
      });
    },
    getProjectMembers() {
      axios
        .get(`/projects/${this.project.id}`)
        .then((res) => {
          this.members = res.data.members.map((e) => {
            return { value: e, text: e.name };
          });
          this.members.push({ text: "Atanmamış", value: null });
        })
        .catch((err) => {
          this.$toastr.error(err.message, "Başarısız!");
        });
    },
  },
};
</script>

<style>
.update-task {
  margin-top: 10px;
  margin-bottom: 10px;
}
.upd-dlt {
  float: left;
}
.dlt {
  margin-left: 10px;
}
.comment {
  word-wrap: break-word;
  max-height: 500px;
  white-space: normal;
  overflow-y: auto;
}
.delete {
  max-width: 50px;
  float: right;
}
.close {
  border:none;
  background: white;
}
</style>