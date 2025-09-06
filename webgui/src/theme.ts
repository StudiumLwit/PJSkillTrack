import {createTheme} from "@mantine/core";

const theme = createTheme({
  fontFamily: 'Inter, sans-serif',
  primaryColor: "blue",
  colors: {
    blue: ['#EDF4FF', '#D6E7FF', '#B0D2FF', '#8ABEFF', '#63A9FF', '#3D94FF', '#2C7BE5', '#2264BA', '#184D8F', '#0E3664'],
    green: ['#E6F9F0', '#C1F1D6', '#8DEDBD', '#56E0A5', '#28C76F', '#1FA25A', '#177E45', '#105A30', '#09361B', '#021207'],
    red: ['#FFECEC', '#FFC9C9', '#FF9E9E', '#FF7373', '#EA5455', '#C73F40', '#A42A2B', '#811516', '#5E0001', '#3B0000'],
    gray: ['#F8FAFC', '#F1F3F6', '#E2E6EA', '#D1D7DE', '#BDC5CE', '#A7B0BA', '#9099A5', '#6E7B8B', '#4D5A6B', '#2C394D'],
  },
  headings: {
    fontFamily: 'Inter, sans-serif',
    sizes: {
      h1: {fontSize: '2.2rem', fontWeight: "700"},
      h2: {fontSize: '1.8rem', fontWeight: "700"},
      h3: {fontSize: '1.5rem', fontWeight: "600"},
    },
  },
  defaultRadius: 'md',
  primaryShade: 6,
  // ###
  autoContrast: true
})

export default theme;
