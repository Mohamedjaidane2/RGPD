/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{html,ts}",
  ],
  theme: {
    extend: {},
    colors: {
      'rgpd_primary': '#E73245',
      'rgpd_secondary': '#F6F6EF',
      'rgpd-third':'#f5b707',
      'rgpd-succ':'#10a905'
    },
  },
  plugins: [require("daisyui")],
}


