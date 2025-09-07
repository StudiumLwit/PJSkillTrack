import react from '@vitejs/plugin-react'
import {defineConfig} from 'vite'

export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      "^/(api|login|logout)": {
        target: "http://localhost:8080",
        changeOrigin: true,
      }
    },
  },
})
