/*
 * Copyright 2000-2013 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package git4idea.actions;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.vcs.log.Hash;
import git4idea.branch.GitBranchUtil;
import git4idea.branch.GitBrancher;
import git4idea.repo.GitRepository;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public class GitCreateNewBranchAction extends GitLogSingleCommitAction {

  @Override
  protected void actionPerformed(@NotNull GitRepository repository, @NotNull Hash commit) {
    Project project = repository.getProject();
    String reference = commit.asString();
    final String name =
      GitBranchUtil.getNewBranchNameFromUser(project, Collections.singleton(repository), "Checkout New Branch From " + reference);
    if (name != null) {
      GitBrancher brancher = ServiceManager.getService(project, GitBrancher.class);
      brancher.checkoutNewBranchStartingFrom(name, reference, Collections.singletonList(repository), null);
    }
  }
}
