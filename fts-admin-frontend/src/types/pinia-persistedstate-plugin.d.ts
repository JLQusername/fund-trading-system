declare module 'pinia-persistedstate-plugin' {
    import { PiniaPluginContext } from 'pinia';
  
    export function createPersistedState(): (context: PiniaPluginContext) => void;
  }
  