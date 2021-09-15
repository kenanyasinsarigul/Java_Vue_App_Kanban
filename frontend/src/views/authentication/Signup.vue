<template>
  <div class="vue-tempalte">
    <b-container class="bv-example-row">
      <b-row class="justify-content-md-center">
        <b-col col lg="6">
          <b-card class="mt-5">
            <h3>Kayıt ol</h3>
            <form @submit.prevent="register">
              <div class="form-group">
                <label
                  style="
                    font-weight: bold;
                    margin-bottom: 10px;
                    margin-top: 10px; ;
                  "
                  >Ad</label
                >
                <input
                  type="text"
                  class="form-control form-control-lg"
                  v-model="entity.name"
                  :class="{
                    'is-invalid': submitted && $v.entity.name.$error,
                  }"
                />
                <div
                  v-if="submitted && !$v.entity.name.required"
                  class="invalid-feedback"
                >
                  Lütfen isminizi giriniz.
                </div>
              </div>
              <div class="form-group">
                <label
                  style="
                    font-weight: bold;
                    margin-bottom: 10px;
                    margin-top: 10px; ;
                  "
                  >Adres</label
                >
                <input
                  type="text"
                  class="form-control form-control-lg"
                  v-model="entity.address"
                  :class="{
                    'is-invalid': submitted && $v.entity.address.$error,
                  }"
                />
                <div
                  v-if="submitted && !$v.entity.address.required"
                  class="invalid-feedback"
                >
                  Lütfen adresinizi giriniz.
                </div>
                <div
                  class="invalid-feedback"
                  v-if="submitted && !$v.entity.address.maxLength"
                >
                  Adres en fazla
                  {{ $v.entity.address.$params.maxLength.max }} karakterden
                  oluşmalıdır.
                </div>
              </div>
              <div class="form-group">
                <label
                  style="
                    font-weight: bold;
                    margin-bottom: 10px;
                    margin-top: 10px; ;
                  "
                  >Telefon Numarası</label
                >
                <input
                  type="text"
                  class="form-control form-control-lg"
                  v-model="entity.phone"
                  :class="{
                    'is-invalid': submitted && $v.entity.phone.$error,
                  }"
                />
                <div
                  v-if="submitted && !$v.entity.phone.required"
                  class="invalid-feedback"
                >
                  Lütfen telefon numaranızı giriniz.
                </div>
                <div
                  class="invalid-feedback"
                  v-if="submitted && !$v.entity.phone.numeric"
                >
                  Telefon numarası 10 karakterden oluşmalıdır.
                </div>
                <div
                  class="invalid-feedback"
                  v-if="
                    (submitted && !$v.entity.phone.minLength) ||
                    (submitted && !$v.entity.phone.maxLength)
                  "
                >
                  telefon numarası
                  {{ $v.entity.phone.$params.minLength.min }} haneli olmalıdır.
                </div>
              </div>
              <div class="form-group">
                <label
                  style="
                    font-weight: bold;
                    margin-bottom: 10px;
                    margin-top: 10px; ;
                  "
                  >Doğum Tarihi</label
                >
                <input
                  type="date"
                  class="form-control form-control-lg"
                  v-model="entity.birthday"
                  :class="{
                    'is-invalid': submitted && $v.entity.birthday.$error,
                  }"
                />
                <div
                  v-if="submitted && !$v.entity.birthday.required"
                  class="invalid-feedback"
                >
                  Lütfen doğum tarihinizi giriniz
                </div>
                <div
                  class="invalid-feedback"
                  v-if="submitted && !$v.entity.birthday.maxValue"
                >
                  Doğum tarihi geçerli değil.
                </div>
              </div>
              <div class="form-group">
                <label
                  style="
                    font-weight: bold;
                    margin-bottom: 10px;
                    margin-top: 10px; ;
                  "
                  >Web sitesi</label
                >
                <input
                  type="text"
                  class="form-control form-control-lg"
                  v-model="entity.website"
                  placeholder="http://example.com"
                  :class="{
                    'is-invalid': submitted && $v.entity.website.$error,
                  }"
                />
                <div
                  class="invalid-feedback"
                  v-if="submitted && !$v.entity.website.url"
                >
                  website adresi bir url olmalıdır.
                </div>
              </div>

              <div class="form-group">
                <label
                  style="
                    font-weight: bold;
                    margin-bottom: 10px;
                    margin-top: 10px; ;
                  "
                  >Email adresi</label
                >
                <input
                  type="email"
                  class="form-control form-control-lg"
                  v-model="entity.email"
                  :class="{
                    'is-invalid': submitted && $v.entity.email.$error,
                  }"
                />
                <div
                  v-if="submitted && !$v.entity.email.required"
                  class="invalid-feedback"
                >
                  Lütfen email adresinizi giriniz.
                </div>
              </div>

              <div class="form-group">
                <label
                  style="
                    font-weight: bold;
                    margin-bottom: 10px;
                    margin-top: 10px; ;
                  "
                  >Parola</label
                >
                <input
                  type="password"
                  class="form-control form-control-lg"
                  v-model="entity.password"
                  :class="{
                    'is-invalid': submitted && $v.entity.password.$error,
                  }"
                />
                <div
                  v-if="submitted && !$v.entity.password.required"
                  class="invalid-feedback"
                >
                  Lütfen parolanızı giriniz
                </div>
                <div
                  v-if="
                    (submitted && !$v.entity.password.minLength) ||
                    (submitted && !$v.entity.password.maxLength)
                  "
                  class="invalid-feedback"
                >
                  Parolanız {{ $v.entity.password.$params.minLength.min }} -
                  {{ $v.entity.password.$params.maxLength.max }} aralığında
                  karakter içermelidir.
                </div>
              </div>
              <div class="form-group">
                <label
                  style="
                    font-weight: bold;
                    margin-bottom: 10px;
                    margin-top: 10px; ;
                  "
                  >Parola tekrar</label
                >
                <input
                  type="password"
                  class="form-control form-control-lg"
                  v-model="entity.confirmPassword"
                  :class="{
                    'is-invalid': submitted && $v.entity.confirmPassword.$error,
                  }"
                />
                <div
                  v-if="submitted && !$v.entity.confirmPassword.required"
                  class="invalid-feedback"
                >
                  Lütfen parolanızı tekrar giriniz.
                </div>
                <div
                  class="invalid-feedback"
                  v-if="submitted && !$v.entity.confirmPassword.sameAsPassword"
                >
                  Girilen parolalar birbiri ile uyuşmuyor.
                </div>
              </div>

              <button type="submit" class="btn btn-primary mt-3 pull-left">
                Kayıt ol
              </button>
              <p class="forgot-password pull-right text-right mt-4">
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
import AuthClient from "../../client/Auth";
import {
  required,
  email,
  minLength,
  maxLength,
  sameAs,
  numeric,
  url,
} from "vuelidate/lib/validators";

export default {
  data() {
    return {
      entity: {
        password: null,
        email: null,
        name: null,
        address: null,
        phone: null,
        birthday: null,
        website: null,
        confirmPassword: null,
      },

      submitted: false,
    };
  },
  validations: {
    entity: {
      name: { required },
      address: { required, maxLength: maxLength(120) },
      email: { required, email },
      password: { required, minLength: minLength(6), maxLength: maxLength(32) },
      confirmPassword: { required, sameAsPassword: sameAs("password") },
      phone: {
        required,
        minLength: minLength(10),
        maxLength: maxLength(10),
        numeric,
      },
      birthday: {
        required,
        maxValue: (value) => value < new Date().toISOString(),
      },
      website: { url },
    },
  },
  mounted() {},
  methods: {
    async register(e) {
      this.submitted = true;
      this.$v.$touch();

      if (this.$v.$invalid) {
        return;
      }
      try {
        const data = await AuthClient.register(this.entity);

        this.$toastr.success("kullanıcınız başarıyla oluşturuldu", "Başarılı!");
        this.$router.push("/login");
      } catch (error) {
        this.$toastr.error("Bu kullanıcı adı kullanılmaktadır.", "Başarısız!");
      }
    },
  },
};
</script>