import { createClient } from '@supabase/supabase-js';

// Replace these with your Supabase project credentials
const SUPABASE_URL = 'https://rzfklbtsaxcibtvrcysa.supabase.co';
const SUPABASE_ANON_KEY = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InJ6ZmtsYnRzYXhjaWJ0dnJjeXNhIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzQ0NjIxNTEsImV4cCI6MjA1MDAzODE1MX0.YIXU2sOf6NZ1AXWTxxsor3d37NBeTi_LnmHR8Aw1eDg';

// Create the Supabase client instance
export const supabase = createClient(SUPABASE_URL, SUPABASE_ANON_KEY);
