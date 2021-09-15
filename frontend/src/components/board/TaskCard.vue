<template>
  <div class="bg-white shadow px-3 pt-3 pb-2 border border-white rounded">
    <div class="pull-right">
      <avatar
        v-if="task.assignee"
        :username="task.assignee.name"
        :size="25"
      ></avatar>
      <avatar
        v-else
        username="?"
        backgroundColor="gray"
        color="white"
        :size="25"
      ></avatar>
    </div>
    <font-awesome-icon class="iconId" icon="thumbtack" style="color: gray" />
    <p class="card-title">{{ task.project.projectCode }}-{{ task.id }}</p>

    <p class="taskTitle mt-4">{{ task.title }}</p>

    <div class="d-flex justify-content-between mt-4">
      <small class="text-muted">Due Date: {{ task.dueDate }}</small>

      <font-awesome-icon
        v-if="task.priority == 'HIGHT'"
        icon="chevron-up"
        style="color: red"
      />
      <font-awesome-icon
        v-if="task.priority == 'MEDIUM'"
        icon="chevron-right"
        style="color: blue"
      />
      <font-awesome-icon
        v-if="task.priority == 'LOW'"
        icon="chevron-down"
        style="color: green"
      />

      <badge v-if="task.type" color="primary">{{ task.type }}</badge>
    </div>
  </div>
</template>
<script>
import Badge from "./Badge.vue";
import Avatar from "vue-avatar";

export default {
  data() {
    return {};
  },
  components: {
    Badge,
    Avatar,
  },
  props: {
    task: {
      type: Object,
      default: () => ({}),
    },
  },
  computed: {},
  methods: {},
};
</script>

<style scoped>
.avatar {
  max-height: 20px;
  max-width: 20px;
  display: inline-block;
}
.card-title {
  font-size: 14px;
  font-weight: 600;
}
.taskId {
  color: black;
  font-weight: bolder;
  font-size: 1em;
}
.iconId {
  float: left;
  margin-right: 5px;
  margin-top: 6px;
  font-size: 1em;
}
</style>
