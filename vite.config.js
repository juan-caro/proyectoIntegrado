import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      '/participations': {
        target: 'http://localhost:8080', // La URL de tu servidor de Spring Boot
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/participations/, '/participations')
      },
      '/tournaments': {
        target: 'http://localhost:8080', // La URL de tu servidor de Spring Boot
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/tournaments/, '/tournaments')
      }
    }
  }
});

