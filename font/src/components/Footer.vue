<!-- eslint-disable vue/multi-word-component-names -->
<template lang="pug">
div
  <footer style="background-color:#000000; color:#fff; font-weight:bold; font-size:16px; padding:10px;">
        div(style="position:relative; align-items:center; text-align:center; justify-content:center;")
            div(style="position:relative; display:flex; align-items:center; justify-content:center;")
                div
                    <strong>Connect to my social networks!</strong>
                div(style="margin-left:10px;")
                    a(href="https://contate.me/mirandadev")
                        img(width="26" height="26" src="https://img.icons8.com/color/48/whatsapp--v1.png" alt="whatsapp--v1")
                    a(href="https://instagram.com/joaovictorsouzamiranda?utm_source=qr&igshid=NGExMmI2YTkyZg%3D%3D")
                        img(width="26" height="26" src="https://img.icons8.com/color/48/instagram-new--v1.png" alt="instagram-new--v1")
                    a(href="https://www.linkedin.com/in/joao-victor-souza-7573a1121")
                        img(width="26" height="26" src="https://img.icons8.com/color/48/linkedin.png" alt="linkedin")
            <div style="position:relative; align-items:center; justify-content:center;">
                span {{ new Date().getFullYear() }} — <strong>MirandaDev</strong>
            </div>
  </footer>

</template>
<script>
export default {
  setup() {},
};
</script>

<style scoped></style>
