
<template>
  <div>
    <div class="container mt-4 mb-4" v-if="project">
      <b-button class="create-task" variant="primary" v-b-modal.modal-1
        >Task Oluştur +
      </b-button>
    </div>

    <b-modal id="modal-1" hide-footer title="Task Oluştur">
      <form @submit.prevent="createTask">
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
              v-model="taskData.dueDate"
              :min="min"
              required
            ></b-form-datepicker>
          </b-form-group>
        </div>

        <br />
        <b-button type="submit" variant="success">Kaydet</b-button>
      </form>
    </b-modal>
  </div>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      min: new Date(),
      selected: null,
      members: [],
      taskData: {},
      priority: null,
      prt_options: [
        { value: "HIGHT", text: "YÜKSEK" },
        { value: "MEDIUM", text: "ORTA" },
        { value: "LOW", text: "DÜŞÜK" },
      ],
    };
  },
  props: ["project"],

  mounted() {
    this.getProjectMembers();
    this.resetTask();
  },
  methods: {
    createTask() {
      if (this.taskData.title) {
        axios.post("/task", this.taskData).then((response) => {
          this.$toastr.success("Task oluşturuldu", "Başarılı!");
          this.$emit("created", response.data);
          this.$bvModal.hide("modal-1");
          this.resetTask();
        });
      } else {
        this.$toastr.error("Başlık boş bırakılamaz.", "Hata!");
      }
    },
    resetTask() {
      this.taskData = {
        project: this.project,
        title: null,
        status: "TODO",
        description: null,
        priority: null,
        assignee: null,
        createdDate: new Date(),
        dueDate: null,
      };
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
</style>