<template>
  <div class="vue-template">
    <b-container class="bv-example-row">
      <b-row class="justify-content-md-center">
        <b-col col lg="6">
          <b-card class="mt-5">
            <h3>Yeni Şifre Oluştur</h3>
            <form @submit.prevent="submit">
              <div class="form-group">
                <label>Parola</label>
                <input
                  type="password"
                  class="form-control form-control-lg"
                  autocomplete="off"
                  :class="{
                    'is-invalid': submitted && $v.password.$error,
                  }"
                  v-model="password"
                />
                <div
                  v-if="submitted && !$v.password.required"
                  class="invalid-feedback"
                >
                  Lütfen parolanızı giriniz
                </div>
                <div
                  v-if="
                    (submitted && !$v.password.minLength) ||
                    (submitted && !$v.password.maxLength)
                  "
                  class="invalid-feedback"
                >
                  Parolanız {{ $v.password.$params.minLength.min }} -
                  {{ $v.password.$params.maxLength.max }} aralığında karakter
                  içermelidir
                </div>
              </div>

              <div class="form-group">
                <label>Parola tekrar</label>
                <input
                  type="password"
                  class="form-control form-control-lg"
                  autocomplete="off"
                  :class="{
                    'is-invalid': submitted && $v.confirmPassword.$error,
                  }"
                  v-model="confirmPassword"
                />
                <div
                  v-if="submitted && !$v.confirmPassword.required"
                  class="invalid-feedback"
                >
                  Lütfen parolanızı tekrar giriniz
                </div>
                <div
                  class="invalid-feedback"
                  v-if="submitted && !$v.confirmPassword.sameAsPassword"
                >
                  Girilen parolalar birbiri ile uyuşmuyor
                </div>
              </div>

              <button type="submit" class="btn btn-primary mt-3 pull-right">
                Kaydet
              </button>

              <p class="forgot-password text-right mt-3">
                Zaten bir üyeliğin var mı ?
                <router-link to="/login">Giriş yap</router-link>
              </p>
            </form>
          </b-card>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import axios from "axios";
import {
  required,
  minLength,
  maxLength,
  sameAs,
} from "vuelidate/lib/validators";
export default {
  name: "ResetPassword",
  data() {
    return {
      resetPasswordToken: null,
      password: null,
      confirmPassword: null,
      submitted: false,
    };
  },

  validations: {
    password: { required, minLength: minLength(6), maxLength: maxLength(32) },
    confirmPassword: { required, sameAsPassword: sameAs("password") },
  },

  created() {
    this.resetPasswordToken = this.$route.query.token;
  },

  methods: {
    submit() {
      this.submitted = true;
      this.$v.$touch();

      if (!this.$v.$invalid) {
        var newPasswordDto = {
          password: this.password,
          resetPasswordToken: this.resetPasswordToken,
        };

        axios
          .post("/auth/reset-password/", newPasswordDto)
          .then((r) => {
            this.$toastr.success(r.data.message, "Başarılı!");
            setTimeout(() => {
              this.$router.push("/login");
            }, 2000);
          })
          .catch((e) => {
            this.$toastr.error(e.response.data.message, "Başarısız!");
          });
      } else {
        this.$toastr.warning("Lütfen şifreleri kontrol ediniz", "Başarısız!");
      }
    },
  },
};
</script>

<style>
</style>