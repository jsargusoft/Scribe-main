/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        // Dark theme
        charcoal: '#1D1D1D',
        lightGray: '#F5F5F5',
        mediumBlue: '#0066CC',
        mutedGray: '#B8B8B8',
        coral: '#FF7043',
        turquoise: '#00A7A7',
        darkCharcoal: '#333333',
        deepGray: '#2C2C2C',
        borderGray: '#444444',
        gold: '#FFD700',

        // Light Theme
        lightBlue: '#ADD8E6',
        lightCream: '#F4F1EB',
        darkGray: '#4A4A4A',
        burntOrange: '#C97B34',
        mutedBrown: '#7C6B56',
        warmRedOrange: '#9B4D28',
        richBrown: '#8A5C34',
        charcoalGray: '#2A2A2A',
        lightBeige: '#F8F4E1',
        softGray: '#D4D4D4',
        goldenYellow: '#FFD700',
      },
    },
  },
  plugins: [],
}
