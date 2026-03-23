const API_BASE = process.env.NEXT_PUBLIC_API_URL || "http://localhost:8080";

export async function apiFetch(
  path: string,
  options: RequestInit & { token?: string } = {}
) {
  const { token, ...init } = options;
  const headers: HeadersInit = {
    "Content-Type": "application/json",
    ...(init.headers as Record<string, string>),
  };
  if (token) {
    (headers as Record<string, string>)["Authorization"] = `Bearer ${token}`;
  }

  const res = await fetch(`${API_BASE}${path}`, { ...init, headers });
  if (!res.ok) {
    const text = await res.text();
    throw new Error(text || `HTTP ${res.status}`);
  }
  return res;
}

export interface MeResponse {
  id: string;
  email: string;
  name: string | null;
  status: string;
  defaultCurrency: string;
  timezone: string;
}

export interface AccountSummary {
  id: string;
  name: string;
  type: string;
  currency: string;
  institution: string | null;
}

export interface TransactionSummary {
  id: string;
  accountId: string;
  amount: number;
  currency: string;
  type: string;
  date: string;
  description: string | null;
  categoryId: string | null;
  visibilityScope: string;
}

export async function getMe(token: string): Promise<MeResponse> {
  const res = await apiFetch("/api/v1/me", { token });
  return res.json();
}

export async function getAccounts(token: string): Promise<AccountSummary[]> {
  const res = await apiFetch("/api/v1/accounts", { token });
  const data = await res.json();
  return Array.isArray(data) ? data : [];
}

export async function getTransactions(
  token: string,
  accountId: string
): Promise<TransactionSummary[]> {
  const res = await apiFetch(
    `/api/v1/accounts/${accountId}/transactions`,
    { token }
  );
  const data = await res.json();
  return Array.isArray(data) ? data : [];
}
