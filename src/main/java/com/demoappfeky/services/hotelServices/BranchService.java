package com.demoappfeky.services.hotelServices;

import com.demoappfeky.model.Branch;
import com.demoappfeky.repository.hotelRepo.BranchRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BranchService {

    private final BranchRepository branchRepository;

    public BranchService(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    public Branch save(Branch branch){
        return branchRepository.save(branch);
    }

    public List<Branch> findAll(){
        return branchRepository.findAll();
    }

    public Optional<Branch> findOne(Long id){
        return branchRepository.findById(id);
    }

    public void removeOne(Long id) {
        branchRepository.deleteById(id);
    }
}
