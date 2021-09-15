<template>
  <div class="vue-tempalte">
    <b-container class="bv-example-row">
      <b-row class="justify-content-md-center">
        <b-col col lg="6">
          <b-card class="mt-5">
            <h3>Şifre Sıfırlama Talebi</h3>

            <form @submit.prevent="submit">
              <div class="form-group">
                <label>Email adresi</label>
                <input
                  type="email"
                  class="form-control form-control-lg"
                  v-model="email"
                  @keyup.enter="submit"
                  :class="{
                    'is-invalid': submitted && $v.email.$error,
                  }"
                />
                <div
                  v-if="submitted && !$v.email.required"
                  class="invalid-feedback"
                >
                  Lütfen emailinizi giriniz.
                </div>
                <div
                  class="invalid-feedback"
                  v-if="submitted && !$v.email.email"
                >
                  Geçerli bir email adresi giriniz
                </div>
              </div>

              <button type="submit" class="btn btn-primary pull-right mt-3">
                Gönder
              </button>
            </form>
          </b-card>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>


<script>
import axios from "axios";
import { required, email } from "vuelidate/lib/validators";

export default {
  submitted: false,

  name: "ForgotPassword",
  data() {
    return {
      submitted: false,
      email: null,
    };
  },
  validations: {
    email: { required, email },
  },

  methods: {
    submit() {
      this.submitted = true;

      this.$v.$touch();

      if (this.$v.$invalid) {
        return;
      }
      axios
        .get(`/auth/reset-password/${this.email}`)
        .then((r) => {
          this.projects = r.data;
          this.$toastr.success(r.data.message, "Başarılı!");
        })
        .catch((e) => {
          this.$toastr.error(e.response.data.message, "Başarısız!");
        });
    },
  },
};
</script>