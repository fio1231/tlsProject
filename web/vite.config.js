import {defineConfig} from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vite.dev/config/
export default defineConfig({
  plugins: [vue()],
  server: {
    port: 19190,
    proxy: {
      '/exchange': {
        target: 'http://localhost:19192',
        changeOrigin: true
      },
      '/api': {
        target: 'http://localhost:19192',
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, '')
      },
    }
  }
})
