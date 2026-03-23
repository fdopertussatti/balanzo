"use client";

import { getAccounts, getMe, getTransactions } from "@/lib/api";
import { createClient } from "@/lib/supabase/client";
import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";

export default function DashboardPage() {
  const router = useRouter();
  const [profile, setProfile] = useState<{ name: string; email: string } | null>(
    null
  );
  const [accounts, setAccounts] = useState<
    { id: string; name: string; type: string; currency: string }[]
  >([]);
  const [selectedAccount, setSelectedAccount] = useState<string | null>(null);
  const [transactions, setTransactions] = useState<
    { id: string; amount: number; type: string; date: string; description: string | null }[]
  >([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const supabase = createClient();

    const init = async () => {
      const {
        data: { session },
      } = await supabase.auth.getSession();
      if (!session?.access_token) {
        router.replace("/login");
        return;
      }

      try {
        const [me, accts] = await Promise.all([
          getMe(session.access_token),
          getAccounts(session.access_token),
        ]);
        setProfile({
          name: me.name || me.email,
          email: me.email,
        });
        setAccounts(
          accts.map((a) => ({
            id: a.id,
            name: a.name,
            type: a.type,
            currency: a.currency,
          }))
        );
        if (accts.length > 0 && !selectedAccount) {
          setSelectedAccount(accts[0].id);
        }
      } catch (e) {
        setError(e instanceof Error ? e.message : "Erro ao carregar dados");
      } finally {
        setLoading(false);
      }
    };

    init();
  }, [router]);

  useEffect(() => {
    if (!selectedAccount) return;

    const loadTransactions = async () => {
      const supabase = createClient();
      const {
        data: { session },
      } = await supabase.auth.getSession();
      if (!session?.access_token) return;

      try {
        const txs = await getTransactions(session.access_token, selectedAccount);
        setTransactions(
          txs.map((t) => ({
            id: t.id,
            amount: t.amount,
            type: t.type,
            date: t.date,
            description: t.description,
          }))
        );
      } catch {
        setTransactions([]);
      }
    };

    loadTransactions();
  }, [selectedAccount]);

  const handleSignOut = async () => {
    const supabase = createClient();
    await supabase.auth.signOut();
    router.replace("/login");
    router.refresh();
  };

  if (loading) {
    return (
      <div className="min-h-screen flex items-center justify-center">
        <p className="text-slate-600">Carregando...</p>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-slate-50">
      <header className="bg-white border-b border-slate-200 px-6 py-4 flex items-center justify-between">
        <h1 className="text-xl font-bold text-slate-800">Balanzo</h1>
        <div className="flex items-center gap-4">
          <span className="text-sm text-slate-600">
            {profile?.name} ({profile?.email})
          </span>
          <button
            onClick={handleSignOut}
            className="text-sm text-slate-600 hover:text-slate-900"
          >
            Sair
          </button>
        </div>
      </header>

      <main className="max-w-4xl mx-auto px-6 py-8">
        {error && (
          <div className="mb-6 p-4 bg-amber-50 text-amber-800 rounded-lg text-sm">
            {error}. Verifique se o backend está rodando em{" "}
            {process.env.NEXT_PUBLIC_API_URL || "http://localhost:8080"}
          </div>
        )}

        <section className="mb-8">
          <h2 className="text-lg font-semibold text-slate-800 mb-4">
            Contas
          </h2>
          {accounts.length === 0 ? (
            <p className="text-slate-600">Nenhuma conta cadastrada.</p>
          ) : (
            <div className="flex flex-wrap gap-2">
              {accounts.map((acc) => (
                <button
                  key={acc.id}
                  onClick={() => setSelectedAccount(acc.id)}
                  className={`px-4 py-2 rounded-lg border transition-colors ${
                    selectedAccount === acc.id
                      ? "bg-blue-600 text-white border-blue-600"
                      : "bg-white border-slate-300 text-slate-700 hover:border-slate-400"
                  }`}
                >
                  {acc.name} ({acc.currency})
                </button>
              ))}
            </div>
          )}
        </section>

        {selectedAccount && (
          <section>
            <h2 className="text-lg font-semibold text-slate-800 mb-4">
              Transações
            </h2>
            {transactions.length === 0 ? (
              <p className="text-slate-600">Nenhuma transação.</p>
            ) : (
              <ul className="divide-y divide-slate-200 bg-white rounded-lg border border-slate-200 overflow-hidden">
                {transactions.map((tx) => (
                  <li
                    key={tx.id}
                    className="px-4 py-3 flex items-center justify-between"
                  >
                    <div>
                      <span
                        className={
                          tx.type === "income"
                            ? "text-emerald-600"
                            : "text-slate-800"
                        }
                      >
                        {tx.type === "income" ? "+" : ""}
                        {tx.amount.toLocaleString("pt-BR", {
                          minimumFractionDigits: 2,
                        })}
                      </span>
                      {tx.description && (
                        <span className="ml-2 text-slate-600 text-sm">
                          {tx.description}
                        </span>
                      )}
                    </div>
                    <span className="text-slate-500 text-sm">{tx.date}</span>
                  </li>
                ))}
              </ul>
            )}
          </section>
        )}
      </main>
    </div>
  );
}
