<template>
  <div class="vue-tempalte">
    <b-container class="bv-example-row">
      <b-row class="justify-content-md-center">
        <b-col col lg="6">
          <b-card class="mt-5">
            <h3>Profilim</h3>

            <div class="form-group">
              <label
                style="
                  font-weight: bold;
                  margin-bottom: 10px;
                  margin-top: 10px; ;
                "
                >Adım - Soyadım</label
              >
              <input
                type="text"
                class="form-control form-control-lg"
                :class="{
                  'is-invalid': submitted && $v.entity.name.$error,
                }"
                v-model="entity.name"
              />
              <div
                class="invalid-feedback"
                v-if="submitted && !$v.entity.name.required"
              >
                Bu alanın doldurulması zorunludur
              </div>
              <div
                class="invalid-feedback"
                v-if="submitted && !$v.entity.name.maxLength"
              >
                Proje adı en az
                {{ $v.entity.name.$params.maxLength.max }} karakterden
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
                >Adresim</label
              >
              <input
                type="text"
                class="form-control form-control-lg"
                :class="{
                  'is-invalid': submitted && $v.entity.address.$error,
                }"
                v-model="entity.address"
              />
              <div
                class="invalid-feedback"
                v-if="submitted && !$v.entity.address.required"
              >
                Bu alanın doldurulması zorunludur
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
                >Telefon Numaram</label
              >
              <input
                type="text"
                class="form-control form-control-lg"
                :class="{
                  'is-invalid': submitted && $v.entity.phone.$error,
                }"
                v-model="entity.phone"
                placeholder="xxx-xxx-xx-xx"
              />
              <div
                class="invalid-feedback"
                v-if="submitted && !$v.entity.phone.required"
              >
                Bu alanın doldurulması zorunludur
              </div>
              <div
                class="invalid-feedback"
                v-if="submitted && !$v.entity.phone.numeric"
              >
                Telefon numarası numeric karakterden oluşmalıdır.
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
                >Doğum Tarihim</label
              >
              <input
                @input="$v.entity.birthday.$touch"
                type="date"
                class="form-control form-control-lg"
                :class="{
                  'is-invalid': submitted && $v.entity.birthday.$error,
                }"
                v-model="entity.birthday"
              />
              <div
                class="invalid-feedback"
                v-if="submitted && !$v.entity.birthday.required"
              >
                Bu alanın doldurulması zorunludur
              </div>
              <div
                class="invalid-feedback"
                v-if="submitted && !$v.entity.birthday.maxValue"
              >
                Doğum tarihi geçerli değil
              </div>
            </div>

            <div class="form-group">
              <label
                style="
                  font-weight: bold;
                  margin-bottom: 10px;
                  margin-top: 10px; ;
                "
                >Web sitem</label
              >
              <input
                type="text"
                class="form-control form-control-lg"
                :class="{
                  'is-invalid': submitted && $v.entity.website.$error,
                }"
                v-model="entity.website"
                placeholder="http://example.com"
              />
              <div
                class="invalid-feedback"
                v-if="submitted && !$v.entity.website.url"
              >
                website adresi bir url olmalıdır.
              </div>
            </div>

            <div
              class="form-group d-grid gap-2 d-md-flex justify-content-md-start"
            >
              <input
                type="submit"
                @click="update"
                class="btn btn-success mt-3"
                value="Güncelle"
              />
            </div>
          </b-card>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import axios from "axios";
import {
  required,
  minLength,
  maxLength,
  url,
  numeric,
} from "vuelidate/lib/validators";

export default {
  data() {
    return {
      entity: {},
      submitted: false,
    };
  },
  validations: {
    entity: {
      name: {
        required,
        maxLength: maxLength(64),
      },
      address: {
        required,
        maxLength: maxLength(120),
      },
      phone: {
        numeric,
        minLength: minLength(10),
        maxLength: maxLength(10),
        required,
      },
      birthday: {
        required,
        maxValue: (value) => value < new Date().toISOString(),
      },
      website: {
        url,
      },
    },
  },
  computed: {
    ...mapGetters("Auth", ["account", "jwt"]),
  },
  async mounted() {
    this.entity.id = this.account.id;
    const { data } = await axios.get(`/account`);
    this.entity = { ...data };
  },
  methods: {
    async update() {
      this.submitted = true;
      this.$v.$touch();

      if (!this.$v.$invalid) {
        await axios.put(`/account`, this.entity).then((r) => {
          this.$store.commit("Auth/setAccount", this.entity);
        });

        this.$toastr.success("Profil başarıyla güncellendi", "Başarılı!");
      } else {
        this.$toastr.error(
          "Lütfen geçerli profil bilgileri giriniz",
          "Başarısız!"
        );
      }
    },
  },
};
</script>