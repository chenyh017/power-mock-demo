
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.dianping.cat.Cat;
import com.dianping.cat.Cat.Context;
import com.dianping.cat.message.Message;
import com.dianping.cat.message.Transaction;

public class MutilThreadTransaction {

	private Stack<String> catChildrenId = new Stack<>();

	public static void main(String[] args) {
		MutilThreadTransaction mt = new MutilThreadTransaction();
		mt.test();
	}

	public void test() {
		Transaction t = Cat.newTransaction("test3", "test1");
		int size = 10000;
		try {

			t.setStatus(Message.SUCCESS);
			for (int i = 0; i < size; i++) {
				Cat.logEvent("type" + i, "name");
			}
			catChildrenId.clear();
			for (int i = 0; i < size; i++) {
				CatContext catContext = new CatContext();
				Cat.logRemoteCallClient(catContext);
				catChildrenId.push(catContext.getProperty(""));
			}
			ExecutorService es = Executors.newFixedThreadPool(50);
			es.execute(() -> {
				for (int i = 0; i < size; i++) {
					Transaction t1 = Cat.newTransaction("test1-in", "check");
					t1.setStatus(Message.SUCCESS);
					Context context = new CatContext(catChildrenId.pop());
					Cat.logRemoteCallServer(context);
					try {
						Cat.logEvent("typeabc", "name");
					} catch (Exception e) {
						t1.setStatus(e);
					} finally {
						t1.complete();
					}
				}
			});
			es.shutdown();
			es.awaitTermination(1, TimeUnit.HOURS);
		} catch (Exception e) {
			t.setStatus(e);
		} finally {
			t.complete();
			System.out.println();
		}
	}

	class CatContext implements Context {

		private String childId;

		public CatContext() {
			super();
		}

		public CatContext(String childId) {
			this.childId = childId;
		}

		@Override
		public void addProperty(String key, String value) {
			this.childId = value;
		}

		@Override
		public String getProperty(String key) {
			return childId;
		}

	}
}
