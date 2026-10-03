import react from '@vitejs/plugin-react'
import { defineConfig } from 'vite'

export default defineConfig({
  plugins: [react()],
  server: {
    port: 5173,
    proxy: {
      '/api': {
        // Must match `server.port` in backend/src/main/resources/application.yml (8081).
        // Port 8080 belongs to a stale sibling checkout (E:/SE) whose compiled
        // classes 500 on /api/suppliers and /api/mappings.
        target: process.env.BACKEND_URL || 'http://localhost:8081',
        changeOrigin: true,
      },
    },
  },
})
