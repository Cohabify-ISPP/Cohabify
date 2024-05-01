import { defineConfig } from 'vite';
import vue from '@vitejs/plugin-vue';
import { VitePWA } from 'vite-plugin-pwa';
import { fileURLToPath, URL } from "node:url";

export default defineConfig({
  plugins: [
     vue(),
     VitePWA({
       manifest: {
         // content of manifest
       },
       workbox: {
         // workbox options for generateSW
       }
     })
  ],
  resolve: {
     alias: {
       'eventsource': 'sockjs-client/lib/transport/browser/eventsource.js',
       'events': 'sockjs-client/lib/event/emitter.js',
       'crypto': 'sockjs-client/lib/utils/browser-crypto.js',
       '@': fileURLToPath(new URL("./src", import.meta.url)),
     },
  },
  optimizeDeps: {
     include: ["@fawmi/vue-google-maps", "fast-deep-equal"],
  },
 });