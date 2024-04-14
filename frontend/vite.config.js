import { defineConfig } from 'vite';
import vue from '@vitejs/plugin-vue';
import { VitePWA } from 'vite-plugin-pwa';

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
    alias: [
      {
        find: 'eventsource',
        replacement: './node_modules/sockjs-client/lib/transport/browser/eventsource.js',
      },
      {
        find: 'events',
        replacement: './node_modules/sockjs-client/lib/event/emitter.js',
      },
      {
        find: 'crypto',
        replacement: './node_modules/sockjs-client/lib/utils/browser-crypto.js',
      },
    ],
 },
});